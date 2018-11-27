package ru.otus.l161;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SocketServerDispatcher {
    private static Logger logger = LoggerFactory.getLogger(SocketServerDispatcher.class);
    private static final int THREADS_NUMBER = 10;
    private static ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    private MessageContainer messageContainer;

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private static final int CLIENT_START_DELAY_SEC = 10;
    private static final String DB_SERVICE_START_COMMAND = "java -jar ../L16.1-ms-dbservice/target/L161-novikov.inbox-jar-with-dependencies.jar";
    private static final String FRONT_1_SERVICE_START_COMMAND = "java -jar ../L16.1-ms-front/target/L161-novikov.inbox-jar-with-dependencies.jar 8091 4321";
    private static final String FRONT_2_SERVICE_START_COMMAND = "java -jar ../L16.1-ms-front/target/L161-novikov.inbox-jar-with-dependencies.jar 8092 4322";

    public SocketServerDispatcher() {
        Map<String, Integer> owners = Map.ofEntries(Map.entry("DB-Service",1234), Map.entry("Front-end #1", 4321), Map.entry("Front-end #2", 4322));
        messageContainer = new LBQMessageContainer(owners);
    }

    public static void main(String[] args) {
        SocketServerDispatcher socketServerDispatcher = new SocketServerDispatcher();

        socketServerDispatcher.startChildProccess(scheduledExecutorService);

        try (ServerSocket server = new ServerSocket(3345)) {
            logger.info("Server socket started.");

            while (!server.isClosed()) {
                Socket client = server.accept();
                executor.execute(new SocketClientHandler(client, socketServerDispatcher.getMessageContainer()));
                logger.info("Connection accepted. Push to executor.");
            }
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }

    public void startChildProccess(ScheduledExecutorService scheduledExecutorService){
        scheduledExecutorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(DB_SERVICE_START_COMMAND);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(DB_SERVICE_START_COMMAND);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC + 10, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(FRONT_1_SERVICE_START_COMMAND);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(FRONT_2_SERVICE_START_COMMAND);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}