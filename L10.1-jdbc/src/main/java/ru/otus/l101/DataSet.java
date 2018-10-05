package ru.otus.l101;

public abstract class DataSet {
    private long id;

    public DataSet(long id) {
        this.id = id;
    }

    public DataSet() {
        this.id = 0;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
