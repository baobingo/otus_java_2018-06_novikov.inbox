package ru.otus.l071;

import java.math.BigDecimal;

public interface Controller {
    boolean put(BigDecimal amount);
    boolean get(BigDecimal amount);
    BigDecimal getBalance();
}
