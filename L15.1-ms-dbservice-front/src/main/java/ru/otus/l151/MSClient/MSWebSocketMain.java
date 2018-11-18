package ru.otus.l151.MSClient;

import java.net.URI;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.l151.DBService;

public class MSWebSocketMain {

    @Autowired
    private DBService hibernateDBService;

    public DBService getHibernateDBService() {
        return hibernateDBService;
    }

    public void setHibernateDBService(DBService hibernateDBService) {
        this.hibernateDBService = hibernateDBService;
    }

    public MSWebSocketMain() {
        new Thread(()->{
            String dest = "ws://localhost:8080/L151-ms-websocket-novikov.inbox/ms";
            WebSocketClient client = new WebSocketClient();
            while(true) {
                try {
                    MSWebSocketClient socket = new MSWebSocketClient(hibernateDBService);
                    client.start();
                    URI echoUri = new URI(dest);
                    ClientUpgradeRequest request = new ClientUpgradeRequest();
                    client.connect(socket, echoUri, request);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                try {
                    Thread.sleep(20000);
                }catch (InterruptedException e){}
        }}).start();
    }
}
