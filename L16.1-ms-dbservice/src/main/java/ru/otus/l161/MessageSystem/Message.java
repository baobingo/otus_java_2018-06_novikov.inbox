package ru.otus.l161.MessageSystem;

import com.google.gson.JsonObject;

import java.util.Random;

public class Message {
    private int id;
    private int owner;
    private JsonObject body;

    public Message(int owner, JsonObject body) {
        this.id = new Random().nextInt() & Integer.MAX_VALUE;
        this.owner = owner;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getOwner() {
        return owner;
    }

    public JsonObject getBody() {
        return body;
    }
}
