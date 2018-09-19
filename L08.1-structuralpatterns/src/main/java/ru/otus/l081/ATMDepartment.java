package ru.otus.l081;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ATMDepartment implements ATMObservable {

    private List<ATMObserver> atmPool;

    public ATMDepartment() {
        atmPool = new ArrayList<>(5);
    }

    @Override
    public void registerATM(ATMObserver atm) {
        atmPool.add(atm);
    }

    @Override
    public void removeATM(ATMObserver atm) {
        atmPool.remove(atm);
    }

    @Override
    public BigDecimal notifySendBalance(){
        try {
            return atmPool.stream().map(x -> x.sendBalance()).reduce((x, y) -> x.add(y)).get();
        }catch (NoSuchElementException e){
            return new BigDecimal(0);
        }
    }

    @Override
    public void notifyResetBalance() {
        atmPool.stream().forEach(x->x.resetBalance());
    }
}
