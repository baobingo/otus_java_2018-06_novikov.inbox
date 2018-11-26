package ru.otus.l151.MSClient.message;

import com.google.gson.JsonObject;

public class MessageUtils {
    public static final Message createMessage(Address from, Address to, MessageActions action, String str){
        JsonObject response = new JsonObject();
        response.addProperty("status", str);

        return new Message(from, to, action, response);
    }
}
