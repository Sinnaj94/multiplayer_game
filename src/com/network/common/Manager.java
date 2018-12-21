package com.network.common;

import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Manager<T extends NetworkMessage> implements Runnable {
    // TODO
    MyDataInputStream dis;
    MyDataOutputStream dos;
    Map<MessageType, NetworkMessageHandler<T>> map;
    private WorkingThread workingThread;
    private static int MANAGER_ID = 0;
    private int clientID;

    public MyDataInputStream getDis() {
        return dis;
    }

    public MyDataOutputStream getDos() {
        return dos;
    }

    public Manager(InputStream is, OutputStream os) {
        System.out.println("Adding a new Manager.");
        map = new HashMap<>();
        dis = new MyDataInputStream(is);
        dos = new MyDataOutputStream(os);
        workingThread = new WorkingThread();
        this.clientID = MANAGER_ID++;
    }

    public void register(MessageType messageType, NetworkMessageHandler<T> handler) {
        map.put(messageType, handler);
    }

    public void send(T n) {
        map.get(n.getMessageType()).sendMessage(n, dos);
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte b = dis.readByte();
                MessageType current = MessageType.getMessageTypeByByte(b);
                NetworkMessageHandler n = map.get(current);
                // FIXME : Sometimes I get a Null Pointer here (Network?)
                n.handle(n.getNetworkMessage(dis));
            } catch (IOException e) {
                System.exit(-1);
            }
        }
    }

    @Override
    public String toString() {
        return "Manager " + this.clientID;
    }
}
