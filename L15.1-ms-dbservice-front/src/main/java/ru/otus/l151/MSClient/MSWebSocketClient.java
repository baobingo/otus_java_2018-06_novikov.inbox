package ru.otus.l151.MSClient;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l151.DBService;
import ru.otus.l151.MSClient.message.Address;
import ru.otus.l151.MSClient.message.Message;
import ru.otus.l151.MSClient.message.MessageActions;
import ru.otus.l151.dataSets.UserDataSet;

@WebSocket
public class MSWebSocketClient {

    private Logger logger = LoggerFactory.getLogger(MSWebSocketClient.class);

    private static final Address DBADDRESS = new Address("db");
    private static final Address FRONTADDRESS = new Address("front");

    private Session session;
    private DBService dbService;
    private final Gson gson =  new Gson();


    public MSWebSocketClient(DBService dbService) {
        this.dbService = dbService;
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        logger.info("DB: Message received from server:" + message);
        if(!message.equals("null")) {
            Message receivedMessage = gson.fromJson(message, Message.class);
            if (receivedMessage.getAction() == MessageActions.PUT) {
                UserDataSet userDataSet = gson.fromJson(receivedMessage.getBody(), UserDataSet.class);
                dbService.insertUsers(userDataSet);

                JsonObject response = new JsonObject();
                response.addProperty("status", "user " + userDataSet.getName() + " added");
                System.out.println(receivedMessage);
                sendMessage(gson.toJson(new Message(DBADDRESS, FRONTADDRESS, MessageActions.PUT, response)));
            }
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        logger.info("DB: Connected to server");
        this.session=session;

        JsonObject body= new JsonObject();
        body.addProperty("status", "check new message");
        sendMessage(gson.toJson(new Message(DBADDRESS, DBADDRESS, MessageActions.GET, body)));
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        logger.info("DB: Connection closed");
    }

    public void sendMessage(String str) {
        try {
            session.getRemote().sendString(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
