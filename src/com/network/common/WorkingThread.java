package com.network.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WorkingThread implements Runnable {
    private BlockingQueue<NetworkMessage> messages;

    public WorkingThread() {
        messages = new ArrayBlockingQueue<NetworkMessage>(20);
    }

    public void addMessage(NetworkMessage message) {
        messages.offer(message);
    }

    @Override
    public void run() {
        while(true) {
            try {
                NetworkMessage m = messages.take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
