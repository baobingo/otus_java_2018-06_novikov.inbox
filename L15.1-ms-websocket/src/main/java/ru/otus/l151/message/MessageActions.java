package ru.otus.l151.message;

public enum MessageActions {
    PUT(1, "Insert data"),
    GET(2, "Get data");

    private String desc;
    private int id;

    MessageActions(int id, String name) {
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }
}
