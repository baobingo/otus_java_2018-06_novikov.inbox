package ru.otus.l071;

import java.math.BigDecimal;

public class HumanImpl implements Human{
    @Override
    public void draw(String string) {
        System.out.println(string);
    }

    @Override
    public BigDecimal issue(String amount) {

        System.out.println("Please wait..");
        return new BigDecimal(amount);
    }

    @Override
    public String issue(BigDecimal amount) {
        return amount.toString();
    }
}
