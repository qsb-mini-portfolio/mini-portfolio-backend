package com.qsportfolio.backend.parsing;

import java.io.InputStream;
import java.util.List;

public interface CsvTransactionParser {
    List<RawTransaction> parse(InputStream in);
}
