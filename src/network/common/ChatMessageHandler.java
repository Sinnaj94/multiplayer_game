package network.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageHandler implements NetworkMessageHandler<ChatMessage> {
    private List<DataOutputStream> dataOutputStreams;
    public static Object T = new Object();

    public ChatMessageHandler() {
        dataOutputStreams = new ArrayList<>();
    }

    @Override
    public void sendMessage(NetworkMessage networkMessage, DataOutputStream dos) {
        try {
            ChatMessage c = (ChatMessage) networkMessage;
            dos.write(c.getMessageType().getByte());
            dos.writeUTF(c.toString());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOutputStream(OutputStream os) {
        dataOutputStreams.add(new DataOutputStream(os));
    }

    @Override
    public ChatMessage getNetworkMessage(DataInputStream dis) {
        try {
            return new ChatMessage(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(ChatMessage n) {
        // Chat Thread delivers Message to all the Clients... Otherwise print it out!
        if (dataOutputStreams.size() > 0) {
            for (DataOutputStream d : dataOutputStreams) {
                sendMessage(n, d);
                //notifyAll();
            }
        } else {
            System.out.println(n.toString());
            //notifyAll();
        }
    }
}
