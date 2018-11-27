package ru.otus.l161;

import ru.otus.l161.MessageSystem.MessageContainer;
import ru.otus.l161.MessageSystem.SocketClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketRunner implements Runnable {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private MessageContainer messageContainer;
    private int owner;

    public SocketRunner(MessageContainer messageContainer, int address) {
        this.messageContainer = messageContainer;
        this.owner = address;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10000);
                executor.execute(new SocketClient(messageContainer, owner));
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
