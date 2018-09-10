package ru.otus.l071;

import java.math.BigDecimal;

public class HumanImpl implements Human{
    @Override
    public void draw() {
        System.out.println("Welcome %user%");
    }

    @Override
    public BigDecimal issue(String amount) {
        return new BigDecimal(amount);
    }
}
