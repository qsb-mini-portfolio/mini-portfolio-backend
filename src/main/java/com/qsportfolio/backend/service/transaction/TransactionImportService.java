package com.qsportfolio.backend.service.transaction;

import com.qsportfolio.backend.parsing.CsvTransactionParser;
import com.qsportfolio.backend.parsing.RawTransaction;
import com.qsportfolio.backend.parsing.traderepublic.TradeRepublicCsvParser;
import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.service.stock.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionImportService {

    private static final DateTimeFormatter CSV_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final TransactionRepository transactionRepository;
    private final StockService stockService;
    private final CsvTransactionParser csvTransactionParser = new TradeRepublicCsvParser();

    @Transactional
    public Result importTransactionCsv(UUID userId, InputStream csvStream) {
        List<RawTransaction> raws = csvTransactionParser.parse(csvStream);
        List<Transaction> entities = raws.stream()
            .map(r -> map(userId, r))
            .collect(Collectors.toList());
        transactionRepository.saveAll(entities);
        return new Result(raws.size(), entities.size());
    }

    private Transaction map(UUID userId, RawTransaction r) {
        Stock stock = stockService.createStock(
            r.symbol(),
            r.name()
        );
        float sign = r.type().equals("BUY") ? 1.0f : -1.0f;

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setDate(LocalDateTime.parse(r.date().trim(), CSV_DT));
        transaction.setVolume(sign * Float.parseFloat(r.quantity()));
        transaction.setPrice(Float.parseFloat(r.price()));
        transaction.setStock(stock);
        return transaction;
    }

    public record Result(int detectedRows, int savedRows) {}
}
