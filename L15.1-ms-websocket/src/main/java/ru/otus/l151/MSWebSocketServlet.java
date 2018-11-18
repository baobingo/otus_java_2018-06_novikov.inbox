package ru.otus.l151;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
@WebServlet(name = "Message service", urlPatterns = { "/ms" })
public class MSWebSocketServlet extends WebSocketServlet {
    private final MSEngine msEngine = new MSEngine();

    @Override
    public void configure(WebSocketServletFactory factory)
    {
        factory.getPolicy().setIdleTimeout(10000);
        factory.setCreator(new MSWebSocketCreator(msEngine));
    }
}
