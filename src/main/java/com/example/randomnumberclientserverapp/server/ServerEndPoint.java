package com.example.incrcliservapp.server;

import com.example.incrcliservapp.dao.UserDAO;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/ws/{login}")
public class ServerEndPoint {

    static List<ServerEndPoint> clients = new CopyOnWriteArrayList<>();
    private Session session;
    private final ServerEndPoint.RandomNumberGen randomNumberGen = new ServerEndPoint.RandomNumberGen();

    @OnOpen
    public void onOpen(Session session, @PathParam("login") String login, EndpointConfig _) {
        System.out.println("login in ServerEndPoint:");
        System.out.println(login);
        if (clients.isEmpty()) {
            randomNumberGen.start();
        }
        this.session = session;
        clients.add(this);
        UserDAO.findUserByLogin(login).setClientConnection(this);
        System.out.println("New client added");
        System.out.println(UserDAO.findUserByLogin(login));
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Socket closed: " + reason.getReasonPhrase());
        clients.remove(this);
        System.out.println("One of the clients has logged out");
    }

    /**
     * Broadcasts random number to all connected clients
     *
     * @param message integer value, which has to be broadcasted
     */
    public void broadcast(Integer message) {
        for (ServerEndPoint client : clients) {
            try {
                client.session.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                clients.remove(this);
                System.out.println("Removing client...");
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
    public class RandomNumberGen extends Thread {
        Random random = new Random();
        public int randomNumber = 0;

        @Override
        public void run() {
            while (true) {
                randomNumber = random.nextInt();
                broadcast(randomNumber);
                System.out.println(randomNumber);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
