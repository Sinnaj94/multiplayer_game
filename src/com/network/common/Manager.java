package com.network.common;

import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Manager<T extends NetworkMessage> implements Runnable {
    MyDataInputStream dis;
    MyDataOutputStream dos;
    Map<MessageType, NetworkMessageHandler<T>> map;
    private static int MANAGER_ID = 0;
    private int clientID;
    public volatile boolean inactive = false;
    private String name;
    private Accessor accessor;

    public MyDataInputStream getDis() {
        return dis;
    }

    public MyDataOutputStream getDos() {
        return dos;
    }

    public Accessor getAccessor() {
        return accessor;
    }

    public Manager(InputStream is, OutputStream os) {
        System.out.println("Adding a new Manager.");
        map = new HashMap<>();
        dis = new MyDataInputStream(is);
        dos = new MyDataOutputStream(os);
        this.clientID = MANAGER_ID++;
        accessor = new Accessor();
        new Thread(accessor).start();

    }

    public void register(MessageType messageType, NetworkMessageHandler<T> handler) {
        map.put(messageType, handler);
    }

    public void send(T message) {
        accessor.addMessage(message);
    }

    @Override
    public void run() {
        while (!inactive) {
            try {
                byte b = dis.readByte();
                MessageType current = MessageType.getMessageTypeByByte(b);
                NetworkMessageHandler n = map.get(current);
                // FIXME : Sometimes I get a Null Pointer here (Network?)
                try {
                    n.handle(n.getNetworkMessage(dis));
                } catch (NullPointerException a) {
                    System.out.println("ERROR");
                    System.out.println("b: " + b + " cu: " +current);
                    System.out.println(n);
                }

            } catch (IOException e) {
                inactive = true;
                try {
                    dis.close();
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Manager " + this.clientID;
    }

    public class Accessor implements Runnable {
        BlockingQueue<T> messages;
        public Accessor() {
            messages = new ArrayBlockingQueue<T>(60);
        }

        public void addMessage(T message) {
            try {
                messages.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(!inactive) {
                try {
                    T m = messages.take();
                    map.get(m.getMessageType()).sendMessage(m, dos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
