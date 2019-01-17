package com.network.common;

import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.net.SocketException;

public interface NetworkMessageHandler<N extends NetworkMessage> {
    /**
     * Send the Message over a given DataOutputStream
     *
     * @param networkMessage Implemented NetworkMessage
     * @param dos            DataOutputStream
     */
    void sendMessage(N networkMessage, MyDataOutputStream dos) throws IOException;

    /**
     * Get the actual Network Message
     *
     * @param dis DataInputStream
     * @return Actual Network Message
     */
    N getNetworkMessage(MyDataInputStream dis) throws IOException;

    /**
     * Handle the Message
     *
     * @param networkMessage Given Networkmessage
     */
    void handle(N networkMessage);
}
