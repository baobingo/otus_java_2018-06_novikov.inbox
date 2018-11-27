package ru.otus.l161.MessageSystem;

import java.util.concurrent.LinkedBlockingQueue;

public class LBQMessageContainer implements MessageContainer {
    private final LinkedBlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<Message> outbox = new LinkedBlockingQueue<>();

    @Override
    public Message getFromInbox() {
        return inbox.poll();
    }

    @Override
    public void putToInbox(Message message) {
        try {
            inbox.put(message);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public Message getFromOutbox() {
        return outbox.poll();
    }

    @Override
    public void putToOutbox(Message message) {
        try {
            outbox.put(message);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public int countInbox() {
        return inbox.size();
    }

    @Override
    public int countOutbox() {
        return outbox.size();
    }
}
