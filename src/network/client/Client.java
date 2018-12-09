package network.client;

import game.gameworld.Renderer;
import game.ServerGameLogic;
import helper.Vector2f;
import network.common.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    Manager m;
    Manager m2;
    Scanner s;
    public volatile boolean running;
    private final static Object token = new Object();
    public Client(int port) {
        try {
            running = true;
            s = new Scanner(System.in);
            socket = new Socket("localhost", port);
            m = new Manager(socket.getInputStream(), socket.getOutputStream());
            m.register(MessageType.CHAT, new ChatMessageHandler());
            m.register(MessageType.MOVE, new MoveMessageHandler());
            // Receive the ChatMessages
            Thread t = new Thread(m);
            t.start();
            ServerGameLogic w = new ServerGameLogic();
            w.setToken(token);
            /*Renderer r = new Renderer(w, m);
            r.setToken(token);*/
            Thread wThread = new Thread(w);
            //Thread rThread = new Thread(r);
            wThread.start();
            //rThread.start();
            clientLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientLoop() {
        while (running) {
            String msg = s.nextLine();
            MoveMessage mo = new MoveMessage(new Vector2f(1f, 0f));
            m.send(mo);
        }
    }

    public static void main(String[] args) {
        Client a = new Client(6060);
    }
}
