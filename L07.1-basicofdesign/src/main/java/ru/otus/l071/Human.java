package ru.otus.l071;

import java.math.BigDecimal;

public interface Human {
    void draw(String string);
    BigDecimal issue(String amount);
    String issue(BigDecimal amount);
}
