package ru.otus.l071;

import java.math.BigDecimal;

public class Converter {
    public static BigDecimal toBigDecimal(String amount) {
        return new BigDecimal(amount);
    }
}
