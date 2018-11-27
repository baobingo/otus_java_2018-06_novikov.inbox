package ru.otus.l161;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class LBQMessageContainer implements MessageContainer {
    private final Map<String, Integer> ownersRegistry;
    private final Map<Integer, LinkedBlockingQueue<Message>> postoffice = new HashMap<>();

    public LBQMessageContainer(Map<String, Integer> ownersRegistry) {
        this.ownersRegistry = ownersRegistry;
        ownersRegistry.forEach((k,v)->createMailbox(v));
    }

    public void createMailbox(int owner){
        postoffice.put(owner, new LinkedBlockingQueue<>());
    }

    @Override
    public Message get(int owner) {
        LinkedBlockingQueue<Message> mailbox = postoffice.get(owner);
        return mailbox.poll();
    }

    @Override
    public void put(Message message) {
        LinkedBlockingQueue<Message> mailbox = postoffice.get(message.getOwner());
        try {
            mailbox.put(message);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public int count(int owner) {
        LinkedBlockingQueue<Message> mailbox = postoffice.get(owner);
        return mailbox.size();
    }

    @Override
    public Map<String, Integer> getOwnersRegistry() {
        return ownersRegistry;
    }
}
