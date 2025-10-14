package com.qsportfolio.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateUtils {

    public static LocalDateTime calculateDateTimeFromNow(String offset) {
        LocalDateTime now = LocalDateTime.now();
        offset = offset.toLowerCase();

        if (offset.endsWith("d")) {
            int days = Integer.parseInt(offset.replace("d", ""));
            return now.minusDays(days).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (offset.endsWith("mo")) {
            int months = Integer.parseInt(offset.replace("mo", ""));
            return now.minusMonths(months).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (offset.endsWith("y")) {
            int years = Integer.parseInt(offset.replace("y", ""));
            return now.minusYears(years).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            throw new IllegalArgumentException("Invalid date offset: " + offset);
        }
    }
}