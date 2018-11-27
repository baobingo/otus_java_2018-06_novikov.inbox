package ru.otus.l161;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class SocketClientHandler implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SocketServerDispatcher.class);
    private Socket clientSocket;
    private MessageContainer messageContainer;
    private int owner;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketClientHandler(Socket client, MessageContainer messageContainer) {
        this.clientSocket = client;
        this.messageContainer = messageContainer;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean mailboxExist(int owner){
        return messageContainer.getOwnersRegistry().containsValue(owner);
    }

    public void close(){
        try {
            writer.close();
            reader.close();
            clientSocket.close();
            logger.info("Closing connections & channels - DONE.");
        }catch (IOException e){
            e.printStackTrace();
        }
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
                    messageContainer.put(new Gson().fromJson(json, Message.class));
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
        while(messageContainer.count(getOwner())>0&&!clientSocket.isClosed()){
            writer.println(new Gson().toJson(messageContainer.get(getOwner()),Message.class));
            writer.println();
        }
        writer.println(".");
    }

    @Override
    public void run() {

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            logger.info("Wait identification.");
            String identification = reader.readLine();
            setOwner(Integer.parseInt(identification));
            logger.info("Identification is " + getOwner());

            if(!mailboxExist(getOwner())){
                throw new IOException("Unknown user.");
            }else {
                write();
            }

            read();

            close();

        } catch (IOException e) {
            close();
            e.printStackTrace();
        }
    }
}
