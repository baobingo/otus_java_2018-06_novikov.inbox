package ru.otus.l081;

import java.math.BigDecimal;

public interface ATMObservable {
    BigDecimal notifySendBalance();
    void notifyResetBalance();
    void registerATM(ATMObserver atm);
    void removeATM(ATMObserver atm);
}
