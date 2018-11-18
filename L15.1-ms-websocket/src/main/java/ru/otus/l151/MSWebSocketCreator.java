package ru.otus.l151;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class MSWebSocketCreator implements WebSocketCreator
{
    private MSWebSocket msWebSocket;

    public MSWebSocketCreator(MSEngine msEngine)
    {
        this.msWebSocket = new MSWebSocket(msEngine);
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp)
    {
        return msWebSocket;
    }
}
