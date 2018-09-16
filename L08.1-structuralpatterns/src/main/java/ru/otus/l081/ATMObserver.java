package ru.otus.l081;

import java.math.BigDecimal;

public interface ATMObserver {
    void updateBalance(String balance);
    void resetBalance();
    BigDecimal sendBalance();
}
