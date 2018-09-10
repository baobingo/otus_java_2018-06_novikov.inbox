package ru.otus.l071;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DetectorImplTest {

    Detector detector = new DetectorImpl();

    @Test
    void separate() {
        assertEquals(1, detector.separate(new BigDecimal(150)).get(NOTESVALUE.FIFTY).intValue());
        assertEquals(1, detector.separate(new BigDecimal(150)).get(NOTESVALUE.ONEHUNDRED).intValue());
        assertEquals(2, detector.separate(new BigDecimal(200)).get(NOTESVALUE.ONEHUNDRED).intValue());
    }
}