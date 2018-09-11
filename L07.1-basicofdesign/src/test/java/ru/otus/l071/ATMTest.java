package ru.otus.l071;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    Human human = new HumanImpl();
    Controller controller = new ControllerImpl();

    @Test
    void run() {
        human.draw("Welcome %user%\n" +
                "Type 1/summ to get money\n" +
                "Type 2/summ to put money");

        controller.put(human.issue("160"));
        assertEquals(new BigDecimal(160),controller.getBalance());

        controller.get(human.issue("170"));
        assertEquals(new BigDecimal(160),controller.getBalance());

        controller.get(human.issue("160"));
        assertEquals(new BigDecimal(0),controller.getBalance());

        controller.put(human.issue("200"));
        assertEquals(new BigDecimal(200),controller.getBalance());

        assertEquals(false, controller.get(human.issue("150")));
        assertEquals(new BigDecimal(200),controller.getBalance());

        controller.put(human.issue("50"));
        assertEquals(new BigDecimal(250),controller.getBalance());

        controller.get(human.issue("150"));
        assertEquals(new BigDecimal(100),controller.getBalance());

        controller.get(human.issue("100"));
        assertEquals(new BigDecimal(0),controller.getBalance());

        assertEquals(true, controller.put(human.issue("300")));
        assertEquals(false, controller.get(human.issue("250")));
        assertEquals(true, controller.put(human.issue("50")));
        assertEquals(true, controller.get(human.issue("350")));
    }
}