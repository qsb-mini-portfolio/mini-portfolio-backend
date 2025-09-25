package com.qsportfolio.backend.service.stock;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.service.stock.response.FinnhubPriceResponse;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Service
public class FinnhubRetriever implements StockPriceRetriever {

    private final WebClient webClient;

    @Value("${finnhub_api_key}")
    private String apiKey;

    public FinnhubRetriever(WebClient.Builder webClientBuilder) {
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
            .baseUrl("https://finnhub.io")
            .build();

    }

    @Override
    public float retrievePriceForStock(Stock stock) {
        FinnhubPriceResponse response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1/quote")
                .queryParam("symbol", stock.getSymbol())
                .queryParam("token", apiKey)
                .build())
            .retrieve()
            .bodyToMono(FinnhubPriceResponse.class)
            .block();

        if (response == null) {
            throw new AppException("No data for " + stock.getSymbol());
        }
        return response.getC();
    }
}
