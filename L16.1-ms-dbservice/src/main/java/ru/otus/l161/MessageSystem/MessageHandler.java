package ru.otus.l161.MessageSystem;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161.DBService;
import ru.otus.l161.dataSets.UserDataSet;

public class MessageHandler implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private MessageContainer messageContainer;
    private DBService dbService;
    private Gson gson = new Gson();

    public MessageHandler(MessageContainer messageContainer, DBService dbService) {
        this.messageContainer = messageContainer;
        this.dbService = dbService;
    }

    public Message createMessage(String string, int owner){
        JsonObject body = new JsonObject();
        body.addProperty("status", string);

        return new Message(owner, body);
    }

    @Override
    public void run() {
        while(messageContainer.countInbox()>0){
            Message message = messageContainer.getFromInbox();
            String jsonBody = message.getBody().get("user").getAsString().replaceAll("\\\\", "");
            UserDataSet userDataSet = gson.fromJson(jsonBody, UserDataSet.class);
            dbService.insertUsers(userDataSet);
            messageContainer.putToOutbox(createMessage("Task #" + message.getId() + " completed.", message.getBody().get("from").getAsInt()));
        }
    }
}
