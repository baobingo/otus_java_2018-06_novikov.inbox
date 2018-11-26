package ru.otus.l151.MSClient;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
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
import ru.otus.l151.MSClient.message.MessageUtils;
import ru.otus.l151.dataSets.UserDataSet;

@WebSocket
public class MSWebSocketClient {

    private Logger logger = LoggerFactory.getLogger(MSWebSocketClient.class);

    private static final Address DBADDRESS = new Address("db");
    private static final Address FRONTADDRESS = new Address("front");

    private MSWebSocketMain msWebSocketMain;
    private Session session;
    private DBService dbService;
    private final Gson gson =  new Gson();


    public MSWebSocketClient(DBService dbService, MSWebSocketMain msWebSocketMain) {
        this.dbService = dbService;
        this.msWebSocketMain = msWebSocketMain;
    }

    @OnWebSocketMessage
    public void onText(Session session, String message){
        logger.info("DB: Message received from server:" + message);
        exec(message);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        logger.info("DB: Connected to server");
        this.session=session;
        if(msWebSocketMain.getOutboundMessage().size()>0){
            try{
            sendMessage(msWebSocketMain.getOutboundMessage().take());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }else {
            sendMessage(MessageUtils.createMessage(DBADDRESS, DBADDRESS, MessageActions.GET, "Check new message."));
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        logger.info("DB: Connection closed");
    }

    public void sendMessage(Message message) {
        try {
            session.getRemote().sendString(gson.toJson(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exec(String message){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Message receivedMessage = gson.fromJson(message, Message.class);

        if (receivedMessage.getAction() == MessageActions.PUT) {
            UserDataSet userDataSet = gson.fromJson(receivedMessage.getBody(), UserDataSet.class);

            Runnable task = ()->{
                dbService.insertUsers(userDataSet);
                msWebSocketMain.addMessage(MessageUtils.createMessage(DBADDRESS, FRONTADDRESS, MessageActions.PUT,  "Task completed."));
            };

            executorService.execute(task);
        }

        executorService.shutdown();
    }
}
