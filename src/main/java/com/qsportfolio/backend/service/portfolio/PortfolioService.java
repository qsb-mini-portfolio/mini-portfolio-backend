package com.qsportfolio.backend.service.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qsportfolio.backend.domain.kafka.KafkaTopic;
import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.portfolio.PortfolioDTO;
import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.request.kafka.PortfolioGraphRequestKafka;
import com.qsportfolio.backend.request.kafka.StockPositionRequestKafka;
import com.qsportfolio.backend.request.kafka.TransactionRequestKafka;
import com.qsportfolio.backend.response.kafka.PortfolioGraphResponseKafka;
import com.qsportfolio.backend.security.SecurityUtils;
import com.qsportfolio.backend.service.DateUtils;
import com.qsportfolio.backend.service.kafka.KafkaService;
import com.qsportfolio.backend.service.stock.StockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PortfolioService {

    private static final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
        .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final TransactionRepository transactionRepository;
    private final StockService stockService;
    private final KafkaService kafkaService;

    public PortfolioService(
        TransactionRepository transactionRepository,
        StockService stockService,
        KafkaService kafkaService) {
        this.transactionRepository = transactionRepository;
        this.stockService = stockService;
        this.kafkaService = kafkaService;
    }

    public PortfolioDTO getPortfolio() {
        List<PortfolioByStockDTO> portfolioByStockDTOs = transactionRepository.getPortfolioGroupByStockFromUser(SecurityUtils.getCurrentUser().getId());

        float portfolioPrice = 0;
        float boughtPortfolioPrice = 0;
        for (PortfolioByStockDTO portfolioByStockDTO : portfolioByStockDTOs) {
            stockService.updateStockPrice(portfolioByStockDTO.getStock());
            if (portfolioByStockDTO.getStock().getLastPrice() != null) {
                portfolioByStockDTO.setCurrentPrice(portfolioByStockDTO.getStock().getLastPrice() * portfolioByStockDTO.getVolume());
                portfolioByStockDTO.setYield((portfolioByStockDTO.getCurrentPrice() - portfolioByStockDTO.getBoughtPrice()) / portfolioByStockDTO.getBoughtPrice());
                portfolioPrice += portfolioByStockDTO.getCurrentPrice();
                boughtPortfolioPrice += portfolioByStockDTO.getBoughtPrice();
            }
        }

        for (PortfolioByStockDTO portfolioByStockDTO : portfolioByStockDTOs) {
            if (portfolioByStockDTO.getCurrentPrice() != null) {
                portfolioByStockDTO.setPart(portfolioByStockDTO.getCurrentPrice() / portfolioPrice);
            } else {
                portfolioByStockDTO.setPart(null);
            }
        }

        return new PortfolioDTO(
            portfolioByStockDTOs,
            portfolioPrice,
            boughtPortfolioPrice,
            (portfolioPrice - boughtPortfolioPrice) / boughtPortfolioPrice
        );
    }

    public PortfolioGraphResponseKafka getPortfolioGraph(String period, String interval) {
        PortfolioDTO portfolioDTO = getPortfolio();
        LocalDateTime date = DateUtils.calculateDateTimeFromNow(period);
        List<Transaction> transactions = transactionRepository.findByDateAfterAndUserId(date, SecurityUtils.getCurrentUser().getId());

        List<StockPositionRequestKafka> stocksKafka = portfolioDTO.getPortfolioByStockDTOs().stream()
            .map(portfolioStockDTO -> new StockPositionRequestKafka(portfolioStockDTO.getStock().getSymbol(), portfolioStockDTO.getVolume())).toList();
        List<TransactionRequestKafka> transactionKafka = transactions.stream()
            .map(transaction -> new TransactionRequestKafka(transaction.getDate(), transaction.getStock().getSymbol(), transaction.getVolume())).toList();

        PortfolioGraphRequestKafka payload = new PortfolioGraphRequestKafka(
            period,
            interval,
            stocksKafka,
            transactionKafka
        );
        System.out.println(payload);
        String request;
        try {
            request = mapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new AppException("Unable to serialize payload");
        }
        try {
            String test = kafkaService.sendAndReceive(KafkaTopic.PortfolioGraphRequest.topic, request, 5000);
            System.out.println(test);
            return mapper.readValue(test, PortfolioGraphResponseKafka.class);
        } catch (Exception e) {
            throw new AppException("Unable to get the Stock Price Graph");
        }
    }
}
