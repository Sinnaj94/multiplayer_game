package com.network.common;

import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface NetworkMessageHandler<NetworkMessage> {
    /**
     * Send the Message over a given DataOutputStream
     *
     * @param networkMessage Implemented NetworkMessage
     * @param dos            DataOutputStream
     */
    void sendMessage(NetworkMessage networkMessage, MyDataOutputStream dos);

    /**
     * Get the actual Network Message
     *
     * @param dis DataInputStream
     * @return Actual Network Message
     */
    NetworkMessage getNetworkMessage(MyDataInputStream dis);

    /**
     * Handle the Message
     *
     * @param networkMessage Given Networkmessage
     */
    void handle(NetworkMessage networkMessage);
}
