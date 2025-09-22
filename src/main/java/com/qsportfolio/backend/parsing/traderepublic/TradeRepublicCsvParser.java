package com.qsportfolio.backend.parsing.traderepublic;

import com.qsportfolio.backend.parsing.CsvTransactionParser;
import com.qsportfolio.backend.parsing.RawTransaction;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TradeRepublicCsvParser implements CsvTransactionParser {

    @Override
    public List<RawTransaction> parse(InputStream in) {
        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.setHeaderExtractionEnabled(true);
        csvParserSettings.setLineSeparatorDetectionEnabled(true);
        csvParserSettings.setDelimiterDetectionEnabled(true, ';', ',');
        csvParserSettings.getFormat().setQuote('"');
        CsvParser csvParser = new CsvParser(csvParserSettings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(in, StandardCharsets.UTF_8));
        return records.stream().map(r -> new RawTransaction(
            r.getString("Date"),
            r.getString("Type"),
            r.getString("ISIN"),
            r.getString("Symbol"),
            r.getString("Name"),
            r.getString("Quantity"),
            r.getString("Price"),
            r.getString("Total Amount"),
            r.getString("Fees"),
            r.getString("Currency"),
            r.getString("FX Rate"),
            r.getString("Withholding Tax"),
            r.getString("Notes")
        )).toList();

    }
}
