package ru.otus.l161.dataSets;

import com.google.gson.annotations.Expose;

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
