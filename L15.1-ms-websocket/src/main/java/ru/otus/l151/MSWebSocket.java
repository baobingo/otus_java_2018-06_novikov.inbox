package ru.otus.l151;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l151.message.Message;
import ru.otus.l151.message.MessageActions;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


@WebSocket
public class MSWebSocket {
    Logger logger = LoggerFactory.getLogger(MSWebSocket.class);
    private final Gson gson = new Gson();
    private final MSEngine msEngine;

    public MSWebSocket(MSEngine msEngine) {
        this.msEngine = msEngine;
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        logger.info("Message received:" + message);

        Message receivedMessage = gson.fromJson(message, Message.class);
        if(receivedMessage.getAction() == MessageActions.GET){
            LinkedBlockingQueue<Message> messages = msEngine.getMessagesMap().get(receivedMessage.getTo());
            if (session.isOpen()) {
                try{
                    session.getRemote().sendString(gson.toJson(messages.poll(5000, TimeUnit.MICROSECONDS)));
                    logger.info("Response sent");
                }catch (InterruptedException|NullPointerException e){}
            }
        }
        if(receivedMessage.getAction() == MessageActions.PUT){
            if(!msEngine.getMessagesMap().containsKey(receivedMessage.getTo())){
                msEngine.getMessagesMap().put(receivedMessage.getTo(), new LinkedBlockingQueue<>());
            }

            LinkedBlockingQueue<Message> messages = msEngine.getMessagesMap().get(receivedMessage.getTo());
            try {
                messages.put(receivedMessage);
                logger.info("New message put");
            }catch (InterruptedException e){}
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        logger.info(session.getRemoteAddress().getHostString() + " connected!");
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        logger.info(session.getRemoteAddress().getHostString() + " closed!");
    }
}