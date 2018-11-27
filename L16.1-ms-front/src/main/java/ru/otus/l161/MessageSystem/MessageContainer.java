package ru.otus.l161.MessageSystem;

public interface MessageContainer {
    public Message getFromInbox();
    public void putToInbox(Message message);
    public Message getFromOutbox();
    public void putToOutbox(Message message);
    public int countInbox();
    public int countOutbox();
}
