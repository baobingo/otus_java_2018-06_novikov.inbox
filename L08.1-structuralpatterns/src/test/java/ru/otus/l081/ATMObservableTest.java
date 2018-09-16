package ru.otus.l081;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ATMObservableTest {

    ATMObservableImpl atmDept = new ATMObservableImpl();

    @Test
    void getATMsBalance() {
        assertEquals(new BigDecimal(0),atmDept.notifySendBalance());
        ATMObserver atm = new ATMImpl();
        atm.updateBalance("1000");
        atmDept.registerATM(atm);
        assertEquals(new BigDecimal(1000),atmDept.notifySendBalance());

        ATMObserver atm1 = new ATMImpl();
        atm1.updateBalance("2050");
        atmDept.registerATM(atm1);
        assertEquals(new BigDecimal(3050),atmDept.notifySendBalance());

        ATMObserver atm2 = new ATMImpl();
        atmDept.registerATM(atm2);
        assertEquals(new BigDecimal(3050),atmDept.notifySendBalance());
        atm2.updateBalance("1050");
        assertEquals(new BigDecimal(4100),atmDept.notifySendBalance());

        atmDept.removeATM(atm2);
        assertEquals(new BigDecimal(3050),atmDept.notifySendBalance());
    }

    @Test
    void restartATMsToStartState() {
        ATMObserver atm = new ATMImpl();
        atm.updateBalance("1000");
        atmDept.registerATM(atm);

        ATMObserver atm1 = new ATMImpl();
        atm1.updateBalance("2050");
        atmDept.registerATM(atm1);

        ATMObserver atm2 = new ATMImpl();
        atmDept.registerATM(atm2);
        atm2.updateBalance("1050");
        assertEquals(new BigDecimal(4100),atmDept.notifySendBalance());

        ((ATMImpl) atm).getController().put(Converter.toBigDecimal("1000"));

        assertEquals(new BigDecimal(5100),atmDept.notifySendBalance());

        ((ATMImpl) atm2).getController().put(Converter.toBigDecimal("500"));

        assertEquals(new BigDecimal(5600),atmDept.notifySendBalance());

        atmDept.notifyResetBalance();

        assertEquals(new BigDecimal(4100),atmDept.notifySendBalance());


    }
}