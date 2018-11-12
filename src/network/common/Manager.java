package network.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Manager implements Runnable {
    DataInputStream dis;
    DataOutputStream dos;
    Map<MessageType, NetworkMessageHandler<? extends NetworkMessage>> map;
    private static int MANAGER_ID = 0;
    private int clientID;
    public Manager(InputStream is, OutputStream os) {
        System.out.println("Manager created.");
        map = new HashMap<>();
        dis = new DataInputStream(is);
        dos = new DataOutputStream(os);
        this.clientID = MANAGER_ID++;
    }

    public void register(MessageType messageType, NetworkMessageHandler<? extends NetworkMessage> handler) {
        System.out.println("Registering " + messageType.b + ", " + handler.toString());
        map.put(messageType, handler);
    }

    public void send(NetworkMessage n) {
        map.get(n.getMessageType()).sendMessage(n, dos);
    }

    @Override
    public void run() {
        while(true) {
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
}
