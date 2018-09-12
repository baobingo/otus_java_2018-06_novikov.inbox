package ru.otus.l071;

import java.math.BigDecimal;
import java.util.Map;

public interface Controller {
    boolean put(BigDecimal amount);
    Map<NOTESVALUE,Integer> get(BigDecimal amount);
    BigDecimal getBalance();
}
