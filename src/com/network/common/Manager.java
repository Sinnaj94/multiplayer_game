package com.network.common;

import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
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

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private Socket socket;

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
        registerStreams(is, os);
        this.clientID = MANAGER_ID++;
        accessor = new Accessor();
        new Thread(accessor).start();
    }

    public void writeUsername(String username) {
        try {
            dos.writeUTF(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerStreams(InputStream is, OutputStream os) {
        dis = new MyDataInputStream(is);
        dos = new MyDataOutputStream(os);
    }


    public void register(MessageType messageType, NetworkMessageHandler<T> handler) {
        if(!inactive) {
            map.put(messageType, handler);
        }
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
                n.handle(n.getNetworkMessage(dis));
            } catch (IOException e) {
                close();
            }
        }
    }

    private void close() {
        System.out.println("SOCKET CLOSED");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inactive = true;
    }

    @Override
    public String toString() {
        return "Manager " + this.clientID;
    }

    public class Accessor implements Runnable {
        BlockingQueue<T> messages;
        public Accessor() {
            messages = new ArrayBlockingQueue<>(100000);
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
                    try {
                        map.get(m.getMessageType()).sendMessage(m, dos);
                    } catch(IOException e) {
                        close();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
