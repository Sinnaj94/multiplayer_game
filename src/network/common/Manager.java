package network.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Manager implements Runnable {
    DataInputStream dis;
    DataOutputStream dos;
    Map<MessageType, NetworkMessageHandler<? extends NetworkMessage>> map;
    public Manager(InputStream is, OutputStream os) {
        System.out.println("Manager created.");
        map = new HashMap<>();
        dis = new DataInputStream(is);
        dos = new DataOutputStream(os);
    }

    public void register(MessageType messageType, NetworkMessageHandler<? extends NetworkMessage> handler) {
        map.put(messageType, handler);
    }

    /*private void register(NetworkMessageHandler<NetworkMessage> n) {
        map.put(MessageType.CHAT, n);
    }*/

    public void send(NetworkMessage n) {
        map.get(n.getMessageType()).sendMessage(n, dos);
    }

    @Override
    public void run() {
        while(true) {
            try {
                byte b = dis.readByte();
                MessageType current = MessageType.getMessageTypeByByte(b);
                // TODO: Generics
                NetworkMessageHandler n = map.get(current);
                n.handle(n.getNetworkMessage(dis));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
