package ru.otus.l081;

import java.math.BigDecimal;
import java.util.Map;

public interface Detector {
    Map<NOTESVALUE,Integer> separate(BigDecimal amount);
}
