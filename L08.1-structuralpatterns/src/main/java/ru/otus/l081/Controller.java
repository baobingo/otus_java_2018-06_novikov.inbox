package ru.otus.l081;

import java.math.BigDecimal;
import java.util.Map;

public interface Controller {
    boolean put(BigDecimal amount);
    Map<NOTESVALUE,Integer> get(BigDecimal amount);
    BigDecimal getBalance();
}
