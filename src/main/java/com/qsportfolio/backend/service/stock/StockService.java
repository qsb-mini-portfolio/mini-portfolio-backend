package com.qsportfolio.backend.service.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qsportfolio.backend.domain.kafka.KafkaTopic;
import com.qsportfolio.backend.domain.stock.StockInfo;
import com.qsportfolio.backend.domain.stock.StockType;
import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.errorHandler.StockNotFoundException;
import com.qsportfolio.backend.repository.StockRepository;
import com.qsportfolio.backend.request.kafka.StockPriceGraphRequestKafka;
import com.qsportfolio.backend.response.kafka.StockPriceGraphResponseKafka;
import com.qsportfolio.backend.service.kafka.KafkaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final StockRepository stockRepository;
    private final StockPriceRetriever stockPriceRetriever;
    private final StockInfoRetriever stockInfoRetriever;
    private final KafkaService kafkaService;

    public StockService(StockRepository stockRepository,
                        StockPriceRetriever stockPriceRetriever,
                        StockInfoRetriever stockInfoRetriever,
                        KafkaService kafkaService) {
        this.stockRepository = stockRepository;
        this.stockPriceRetriever = stockPriceRetriever;
        this.stockInfoRetriever = stockInfoRetriever;
        this.kafkaService = kafkaService;
    }

    public Stock createStock(String symbol, String name) {
        Optional<Stock> optionalStock = stockRepository.findFirstBySymbol(symbol);
        if (optionalStock.isPresent()) {
            return optionalStock.get();
        }

        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setName(name);
        stock.setType(StockType.OTHER);
        try {
            float lastPrice = stockPriceRetriever.retrievePriceForStock(stock);
            stock.setLastPrice(lastPrice);
            stock.setPriceDate(LocalDateTime.now());
        } catch (Exception e) {
            // Doesn't throw exception
        }

        try {
            StockInfo info = stockInfoRetriever.retrieveStockInformation(stock);
            stock.setType(info.getType());
            stock.setName(info.getName());
        } catch (Exception e) {
            // Doesn't throw exception
        }

        stockRepository.save(stock);
        return stock;
    }

    public Stock getStockBySymbol(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol).orElseThrow(() -> new StockNotFoundException(symbol));
        updateStockPrice(stock);
        return stock;
    }

    public float getStockCurrentPrice(Stock stock) {
        if (stock.getPriceDate() != null && stock.getPriceDate().toLocalDate().equals(LocalDate.now())) {
            return stock.getLastPrice();
        }

        float lastPrice = stockPriceRetriever.retrievePriceForStock(stock);

        stock.setLastPrice(lastPrice);
        stock.setPriceDate(LocalDateTime.now());
        stockRepository.save(stock);

        return lastPrice;
    }

    public List<Stock> listAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            updateStockPrice(stock);
        }
        return stocks;
    }

    public void updateStockPrice(Stock stock) {
        if (stock.getPriceDate() != null) {
            LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
            if (stock.getPriceDate().isAfter(fiveMinutesAgo)) {
                return;
            }
        }

        try {
            float lastPrice = stockPriceRetriever.retrievePriceForStock(stock);

            stock.setLastPrice(lastPrice);
            stock.setPriceDate(LocalDateTime.now());
            stockRepository.save(stock);
        } catch (Exception e) {
            // Do nothing
        }
    }

    public StockPriceGraphResponseKafka getStockPriceGraph(String symbol, String period) {
        StockPriceGraphRequestKafka payload = new StockPriceGraphRequestKafka(symbol, period);
        String request;
        try {
            request = mapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new AppException("Unable to serialize payload");
        }
        try {
            return mapper.readValue(kafkaService.sendAndReceive(KafkaTopic.StockPriceGraphRequest.topic, request, 5000), StockPriceGraphResponseKafka.class);
        } catch (Exception e) {
            throw new AppException("Unable to get the Stock Price Graph");
        }
    }
}
