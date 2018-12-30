package com.network.common;

import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Manager<T extends NetworkMessage> implements Runnable {
    MyDataInputStream dis;
    MyDataOutputStream dos;
    Map<MessageType, NetworkMessageHandler<T>> map;
    private WorkingThread workingThread;
    private static int MANAGER_ID = 0;
    private int clientID;
    public volatile boolean inactive = false;
    private String name;

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
        workingThread = WorkingThread.getInstance();
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
        while (!inactive) {
            try {
                byte b = dis.readByte();
                MessageType current = MessageType.getMessageTypeByByte(b);
                NetworkMessageHandler n = map.get(current);
                // FIXME : Sometimes I get a Null Pointer here (Network?)
                workingThread.add(n.getNetworkMessage(dis), n);
            } catch (IOException e) {
                inactive = true;
                try {
                    dis.close();
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println(String.format("Bye, bye!"));
            }
        }
    }

    @Override
    public String toString() {
        return "Manager " + this.clientID;
    }
}
