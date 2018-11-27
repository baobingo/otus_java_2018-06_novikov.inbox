package ru.otus.l161;

import java.util.Map;

public interface MessageContainer {
    public Message get(int owner);
    public void put(Message message);
    public int count(int owner);
    public void createMailbox(int owner);
    public Map<String, Integer> getOwnersRegistry();
}
