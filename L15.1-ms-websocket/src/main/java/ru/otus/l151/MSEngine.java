package ru.otus.l151;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l151.message.Address;
import ru.otus.l151.message.Message;
import ru.otus.l151.message.MessageActions;
import ru.otus.l151.message.MessageUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MSEngine {
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
    Logger logger = LoggerFactory.getLogger(MSEngine.class);
    private final Gson gson = new Gson();

    public MSEngine() {
        this.messagesMap = new HashMap<>();
    }

    public Map<Address, LinkedBlockingQueue<Message>> getMessagesMap() {
        return messagesMap;
    }

    public String proccessMessage(String message){
        String result = null;
        Message receivedMessage = gson.fromJson(message, Message.class);

        if(receivedMessage.getAction() == MessageActions.GET){
            LinkedBlockingQueue<Message> messages = getMessagesMap().get(receivedMessage.getTo());
            try {
                Message lastMessage = messages.poll(5000, TimeUnit.MICROSECONDS);
                if(lastMessage == null){
                    throw new NullPointerException();
                }
                result = gson.toJson(lastMessage);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        if(receivedMessage.getAction() == MessageActions.PUT){
            if(!getMessagesMap().containsKey(receivedMessage.getTo())){
                getMessagesMap().put(receivedMessage.getTo(), new LinkedBlockingQueue<>());
            }
            LinkedBlockingQueue<Message> messages = getMessagesMap().get(receivedMessage.getTo());
            try {
                messages.put(receivedMessage);
                result = gson.toJson(MessageUtils.createMessage(receivedMessage.getTo(),receivedMessage.getFrom(), MessageActions.GET, "Message received and put in queue."), Message.class);
            }catch (InterruptedException e){}
        }

        return result;
    }
}
