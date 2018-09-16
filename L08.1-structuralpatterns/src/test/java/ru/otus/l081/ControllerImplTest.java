package ru.otus.l081;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ControllerImplTest {

    Controller controller = new ControllerImpl();

    @Test
    void put() {
        controller.put(new BigDecimal(12550));
        assertEquals(new BigDecimal(12550),controller.getBalance());
    }

    @Test
    void get() {
        controller.put(new BigDecimal(160));
        assertEquals(new BigDecimal(160),controller.getBalance());

        controller.get(new BigDecimal(170));
        assertEquals(new BigDecimal(160),controller.getBalance());

        controller.get(new BigDecimal(160));
        assertEquals(new BigDecimal(0),controller.getBalance());

        controller.put(new BigDecimal(200));
        assertEquals(new BigDecimal(200),controller.getBalance());

        controller.get(new BigDecimal(150));
        assertEquals(new BigDecimal(200),controller.getBalance());

        controller.put(new BigDecimal(50));
        assertEquals(new BigDecimal(250),controller.getBalance());

        controller.get(new BigDecimal(150));
        assertEquals(new BigDecimal(100),controller.getBalance());

    }

    @Test
    void getBalance() {
        controller.put(new BigDecimal(2550));
        assertEquals(new BigDecimal(2550),controller.getBalance());
    }
}