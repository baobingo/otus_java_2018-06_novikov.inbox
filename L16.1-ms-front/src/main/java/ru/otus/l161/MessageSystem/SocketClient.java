package ru.otus.l161.MessageSystem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient implements Runnable{

    private int front_id;
    private Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private Socket socket;
    private MessageContainer messageContainer;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketClient(MessageContainer messageContainer, int owner) {
        this.messageContainer = messageContainer;
        this.front_id = owner;
    }

    public void read(){
        logger.info("Receiving inbox.");
        try {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) { //blocks
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    final String json = stringBuilder.toString();
                    logger.info("Receiving message: " + json);
                    messageContainer.putToInbox(new Gson().fromJson(json, Message.class));
                    stringBuilder = new StringBuilder();
                }
                if (inputLine.equals(".")) {
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void write(){
        logger.info("Sending outbox.");
        while(messageContainer.countOutbox()>0&&!socket.isClosed()){
            writer.println(new Gson().toJson(messageContainer.getFromOutbox(), Message.class));
            writer.println();
        }
        writer.println(".");
    }

    @Override
    public void run() {
        try{
            socket = new Socket("localhost", 3345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            logger.info("Client connected to socket.");
            logger.info("Sending identification.");
            writer.println(String.valueOf(front_id));
            logger.info("Identification sent.");

            logger.info("Wait inbox messages.");
            read();
            logger.info("Inbox messages recieved.");

            logger.info("Send outbox messages");

            JsonObject body = new JsonObject();
            body.addProperty("status", "ok");

            write();

            writer.close();
            reader.close();

            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
