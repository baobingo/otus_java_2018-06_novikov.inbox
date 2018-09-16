package ru.otus.l081;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class DetectorImpl implements Detector {
    @Override
    public Map<NOTESVALUE, Integer> separate(BigDecimal amount) {
        Map<NOTESVALUE, Integer> bills = new TreeMap<>();

        for(NOTESVALUE note: NOTESVALUE.values()){
            int count = amount.divide(new BigDecimal(note.getRate())).intValue();
            bills.put(note, count);
            amount = amount.subtract(new BigDecimal(note.getRate() * count));
        }

        return bills;
    }
}
