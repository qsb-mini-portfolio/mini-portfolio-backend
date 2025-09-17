package com.qsportfolio.backend.service.stockPrice;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class PolygonIORetriever implements StockPriceRetriever {

    private final WebClient webClient;

    @Value("${polygon_api_key}")
    private String apiKey;

    public PolygonIORetriever(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create()
                    .secure(sslContextSpec -> {
                        try {
                            sslContextSpec.sslContext(
                                SslContextBuilder.forClient()
                                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                    .build()
                            );
                        } catch (SSLException e) {
                            throw new RuntimeException(e);
                        }
                    })
            ))
            .baseUrl("https://api.polygon.io")
            .build();

    }

    @Override
    public float retrievePriceForStock(Stock stock, LocalDateTime dateTime) {
        String formattedDate = dateTime.toLocalDate().toString();
        long targetMillis = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

        PolygonResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v2/aggs/ticker/{symbol}/range/1/minute/{from}/{to}")
                .queryParam("apiKey", apiKey)
                .build(stock.getSymbol(), formattedDate, formattedDate))
            .retrieve()
            .bodyToMono(PolygonResponse.class)
            .block();

        if (response != null && response.getResults() != null) {
            return (float) response.getResults().stream()
                .filter(r -> r.getT() == targetMillis)
                .findFirst()
                .orElseThrow(() -> new AppException("No price found at " + dateTime))
                .getC();
        }

        throw new AppException("No data for " + stock.getSymbol() + " on " + formattedDate);
    }

    @Override
    public float retrievePriceForStock(Stock stock) {

        PolygonResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v2/aggs/ticker/{symbol}/prev")
                .queryParam("apiKey", apiKey)
                .build(stock.getSymbol()))
            .retrieve()
            .bodyToMono(PolygonResponse.class)
            .block();

        try {
            return response.getResults().get(0).getC();
        } catch (Exception e) {
            throw new AppException("No data for " + stock.getSymbol());
        }
    }
}
