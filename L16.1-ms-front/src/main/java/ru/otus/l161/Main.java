package ru.otus.l161;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.l161.MessageSystem.LBQMessageContainer;
import ru.otus.l161.MessageSystem.MessageContainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static int PORT = 8090;
    private static int ADDRESS = 4321;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws Exception {
        if(args.length == 2){
            PORT = Integer.valueOf(args[0]);
            ADDRESS = Integer.valueOf(args[1]);
        }

        ResourceHandler resourceHandler = new ResourceHandler();

        MessageContainer messageContainer = new LBQMessageContainer();
        executor.execute(new SocketRunner(messageContainer, ADDRESS));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor(resourceHandler);

        context.addServlet(new ServletHolder(new UserAddServlet(templateProcessor, messageContainer, ADDRESS)), "/users");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();

    }
}
