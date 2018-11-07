package network.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface NetworkMessageHandler<T extends NetworkMessage> {
    public void sendMessage(NetworkMessage networkMessage, DataOutputStream dos);
    public T getNetworkMessage(DataInputStream dis);
    public void handle(T networkMessage);
}
