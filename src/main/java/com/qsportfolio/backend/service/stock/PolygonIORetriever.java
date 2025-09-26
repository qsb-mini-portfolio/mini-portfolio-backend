package com.qsportfolio.backend.service.stock;

import com.qsportfolio.backend.domain.stock.StockInfo;
import com.qsportfolio.backend.domain.stock.StockType;
import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.service.stock.response.PolygonPriceResponse;
import com.qsportfolio.backend.service.stock.response.PolygonTypeResponse;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Service
@Primary
public class PolygonIORetriever implements StockPriceRetriever, StockInfoRetriever {

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
    public float retrievePriceForStock(Stock stock) {
        PolygonPriceResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v2/aggs/ticker/{symbol}/prev")
                .queryParam("apiKey", apiKey)
                .build(stock.getSymbol()))
            .retrieve()
            .bodyToMono(PolygonPriceResponse.class)
            .block();

        try {
            return response.getResults().get(0).getC();
        } catch (Exception e) {
            throw new AppException("No data for " + stock.getSymbol());
        }
    }

    @Override
    public StockInfo retrieveStockInformation(Stock stock) {
        PolygonTypeResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v3/reference/tickers/{symbol}")
                .queryParam("apiKey", apiKey)
                .build(stock.getSymbol()))
            .retrieve()
            .bodyToMono(PolygonTypeResponse.class)
            .block();

        try {
            return new StockInfo(
                StockType.fromSicCode(Integer.parseInt(response.getResults().getSic_code())),
                response.getResults().getName()
            );
        } catch (Exception e) {
            throw new AppException("No data for " + stock.getSymbol());
        }
    }
}
