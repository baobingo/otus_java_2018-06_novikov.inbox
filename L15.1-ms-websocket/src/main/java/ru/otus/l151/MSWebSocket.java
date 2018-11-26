package ru.otus.l151;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@WebSocket
public class MSWebSocket {
    Logger logger = LoggerFactory.getLogger(MSWebSocket.class);
    private final MSEngine msEngine;

    public MSWebSocket(MSEngine msEngine) {
        this.msEngine = msEngine;
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        logger.info("Message received:" + message);
        if (session.isOpen()) {
            try{
                session.getRemote().sendString(msEngine.proccessMessage(message));
                logger.info("Response sent");
            }catch (NullPointerException e){
            }
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