package network.client;

import game.gameworld.Renderer;
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
    public Client(int port) {
        try {
            ClientGameLogic c = new ClientGameLogic();
            Renderer r = new Renderer();
            Thread t = new Thread(r);
            t.start();
            running = true;
            s = new Scanner(System.in);
            socket = new Socket("localhost", port);
            m = new Manager(socket.getInputStream(), socket.getOutputStream());
            m.register(MessageType.GAME_OBJECT, new GameObjectMessageHandler());
            // Receive the ChatMessages
            Thread ta = new Thread(m);
            ta.start();
            /*ServerGameLogic w = new ServerGameLogic();
            w.setToken(token);
            /*Renderer r = new Renderer(w, m);
            r.setToken(token);
            Thread wThread = new Thread(w);
            //Thread rThread = new Thread(r);
            wThread.start();
            //rThread.start();
            clientLoop();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientLoop() {
        while (running) {

        }
    }

    public static void main(String[] args) {
        Client a = new Client(6060);
    }
}
