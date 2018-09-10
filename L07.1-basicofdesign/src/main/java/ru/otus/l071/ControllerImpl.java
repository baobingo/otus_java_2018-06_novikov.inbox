package ru.otus.l071;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ControllerImpl implements Controller {

    Detector detector;
    Map<NOTESVALUE,Cell> cells;

    public ControllerImpl() {
        cells = new TreeMap<>();
        Arrays.stream(NOTESVALUE.values()).forEach(x->cells.put(x,new CellImpl()));
        detector = new DetectorImpl();
    }

    @Override
    public boolean put(BigDecimal amount) {
        detector.separate(amount).entrySet().stream().forEach(
                x->cells.get(x.getKey()).put(x.getValue()));
        return true;
    }

    @Override
    public boolean get(BigDecimal amount){
        BigDecimal AMOUNT = amount;

        if(getBalance().compareTo(amount)<0){
            return false;
        }

        Map<NOTESVALUE,Integer> bills = new HashMap<>();

        for (Map.Entry<NOTESVALUE, Cell> entry : cells.entrySet()) {
            int count = amount.divide(new BigDecimal(entry.getKey().getRate())).intValue();
            if(entry.getValue().getCurrentCount()>=count) {
                bills.put(entry.getKey(), count);
                amount = amount.subtract(new BigDecimal(entry.getKey().getRate() * count));
            }
        }

        if(new BigDecimal(bills.entrySet().stream().mapToInt(x->x.getValue()*x.getKey().getRate()).sum()).compareTo(AMOUNT)==0){
            bills.entrySet().stream().forEach(x->cells.get(x.getKey()).get(x.getValue()));
            return true;
        }

        return false;
    }

    @Override
    public BigDecimal getBalance() {
        return new BigDecimal(cells.entrySet().stream().mapToInt(x->x.getKey().getRate()*x.getValue().getCurrentCount()).sum());
    }
}
