package ru.otus.l121.dataSets;

import javax.persistence.*;

@MappedSuperclass
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public DataSet() {
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }
}
