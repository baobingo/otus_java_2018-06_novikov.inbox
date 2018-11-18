package ru.otus.l151;

import ru.otus.l151.message.Address;
import ru.otus.l151.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class MSEngine {
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;

    public MSEngine() {
        this.messagesMap = new HashMap<>();
    }

    public Map<Address, LinkedBlockingQueue<Message>> getMessagesMap() {
        return messagesMap;
    }
}
