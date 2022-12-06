package com.inditex.msprices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PriceUtils {

    private final static String DATE_PATTERN = "yyyy-MM-dd-HH.mm.ss";
    private final static String PRICE_PATTERN = "%s %s";
    private final static String DECIMAL_FORMAT_PATTERN = "0.00";

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String localDateToString(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    public static LocalDateTime StringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DATE_FORMATTER);
    }

    public static String finalPrice(BigDecimal price, String currency) {
        if (price == null || currency == null) {
            return null;
        }
        price = price.setScale(2, RoundingMode.HALF_UP);
        return String.format(PRICE_PATTERN, new DecimalFormat(DECIMAL_FORMAT_PATTERN).format(price), currency);
    }

    public static Boolean isBetweenOrEqual(boolean equalsFlag, LocalDateTime startOffsetDate, LocalDateTime endOffsetDate, LocalDateTime date) {
        if (equalsFlag) {
            return (date.isAfter(startOffsetDate) || date.isEqual(startOffsetDate))
                    && (date.isBefore(endOffsetDate) || date.isEqual(endOffsetDate));
        }
        return date.isAfter(startOffsetDate) && date.isBefore(endOffsetDate);
    }
}
