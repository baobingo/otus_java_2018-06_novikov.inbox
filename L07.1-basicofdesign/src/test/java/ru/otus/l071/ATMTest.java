package ru.otus.l071;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    Controller controller = new ControllerImpl();

    @Test
    void run() {
        System.out.println("Welcome %user%\n" +
                "Type 1/summ to get money\n" +
                "Type 2/summ to put money");

        controller.put(Converter.toBigDecimal("160"));
        assertEquals(new BigDecimal(160),controller.getBalance());

        controller.get(Converter.toBigDecimal("170"));
        assertEquals(new BigDecimal(160),controller.getBalance());

        controller.get(Converter.toBigDecimal("160"));
        assertEquals(new BigDecimal(0),controller.getBalance());

        controller.put(Converter.toBigDecimal("200"));
        assertEquals(new BigDecimal(200),controller.getBalance());

        assertEquals(null, controller.get(Converter.toBigDecimal("150")));
        assertEquals(new BigDecimal(200),controller.getBalance());

        controller.put(Converter.toBigDecimal("50"));
        assertEquals(new BigDecimal(250),controller.getBalance());

        controller.get(Converter.toBigDecimal("150"));
        assertEquals(new BigDecimal(100),controller.getBalance());

        controller.get(Converter.toBigDecimal("100"));
        assertEquals(new BigDecimal(0),controller.getBalance());

        assertEquals(true, controller.put(Converter.toBigDecimal("300")));
        assertEquals(null, controller.get(Converter.toBigDecimal("250")));
        assertEquals(true, controller.put(Converter.toBigDecimal("50")));
        assertEquals(true, controller.get(Converter.toBigDecimal("350")).size()>0);
    }
}