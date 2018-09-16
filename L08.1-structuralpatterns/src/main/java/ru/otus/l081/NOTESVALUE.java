package ru.otus.l081;

public enum NOTESVALUE {
    FIVETHOUSAND(5000), TWOTHOUSAND(2000), THOUSAND(1000),
        FIVEHUNDRED(500), ONEHUNDRED(100),TWOHUNDRED(200),
           FIFTY(50),TEN(10);

    private final int value;

    NOTESVALUE(final int newValue) {
        value = newValue;
    }

    public int getRate() { return value; }
}
