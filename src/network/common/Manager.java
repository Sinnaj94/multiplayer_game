package network.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Manager<T extends NetworkMessage> implements Runnable {
    // TODO
    DataInputStream dis;
    DataOutputStream dos;
    Map<MessageType, NetworkMessageHandler<T>> map;
    private static int MANAGER_ID = 0;
    private int clientID;

    public Manager(InputStream is, OutputStream os) {
        map = new HashMap<>();
        dis = new DataInputStream(is);
        dos = new DataOutputStream(os);
        this.clientID = MANAGER_ID++;
    }

    public void register(MessageType messageType, NetworkMessageHandler<T> handler) {
        System.out.println("Registering " + messageType.b + ", " + handler.toString());
        map.put(messageType, handler);
    }

    public void send(T n) {
        map.get(n.getMessageType()).sendMessage(n, dos);
    }

    @Override
    public void run() {
        System.out.println("New Manager Thread with ID " + clientID);
        while (true) {
            try {
                byte b = dis.readByte();
                MessageType current = MessageType.getMessageTypeByByte(b);
                NetworkMessageHandler n = map.get(current);
                // FIXME: Generics
                n.handle(n.getNetworkMessage(dis));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Manager " + this.clientID;
    }
}
