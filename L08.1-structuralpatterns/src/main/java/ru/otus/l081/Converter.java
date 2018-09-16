package ru.otus.l081;

import java.math.BigDecimal;

public class Converter {
    public static BigDecimal toBigDecimal(String amount) {
        return new BigDecimal(amount);
    }
}
