package ru.otus.l151.MSClient.message;

import com.google.gson.JsonObject;

public class Message {
    private final Address from;
    private final Address to;
    private final MessageActions action;
    private final JsonObject body;

    public Message(Address from, Address to, MessageActions action, JsonObject body) {
        this.from = from;
        this.to = to;
        this.action = action;
        this.body = body;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public MessageActions getAction() {
        return action;
    }

    public JsonObject getBody() {
        return body;
    }
}
