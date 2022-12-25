package com.example.randomnumberclientserverapp.server;

import com.example.randomnumberclientserverapp.dao.UserDAO;
import com.example.randomnumberclientserverapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/ws/{login}")
public class ServerEndPoint {
    private final static Logger log = LoggerFactory.getLogger(User.class);

    static List<ServerEndPoint> clients = new CopyOnWriteArrayList<>();
    private Session session;

    private static final ServerEndPoint.RandomNumberGen randomNumberGen = new ServerEndPoint.RandomNumberGen();

    @OnOpen
    public void onOpen(Session session, @PathParam("login") String login, EndpointConfig _) {
        log.info("Login passed to ServerEndPoint: {}", login);
        if (clients.isEmpty()) {
            randomNumberGen.start();
        }
        this.session = session;
        clients.add(this);
        UserDAO.findUserByLogin(login).setClientConnection(this);
        log.info("New client added: {}", UserDAO.findUserByLogin(login));
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.warn("Socket closed: {}", reason.getReasonPhrase());
        clients.remove(this);
        log.info("One of the clients has logged out");
        if (clients.isEmpty()) {
            randomNumberGen.disable();
        }
    }

    /**
     * Broadcasts random number to all connected clients
     *
     * @param message integer value, which has to be broadcasted
     */
    public static void broadcast(Integer message) {
        for (ServerEndPoint client : clients) {
            try {
                client.session.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                clients.remove(client);
                log.info("Removing client...");
                try {
                    client.session.close();
                } catch (IOException e1) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ClientConnection{" +
                "session=" + session +
                '}';
    }

    /**
     * Inner class, which generates random number
     */
    private static class RandomNumberGen extends Thread {
        Random random = new Random();
        public int randomNumber = 0;
        private boolean isActive;

        private RandomNumberGen() {
            this.isActive = true;
        }

        public void disable() {
            this.isActive = false;
        }

        @Override
        public void run() {
            this.isActive = true;
            log.info("Broadcasting started");
            while (this.isActive) {
                randomNumber = random.nextInt();
                broadcast(randomNumber);
                log.info("Broadcasted: {}", randomNumber);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
