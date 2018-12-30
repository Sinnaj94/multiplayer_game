package com.network.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WorkingThread implements Runnable {
    private static WorkingThread instance;

    private BlockingQueue<NetworkMessage> messages;
    private BlockingQueue<NetworkMessageHandler> messageHandlers;

    public static WorkingThread getInstance() {
        if (instance == null) {
            WorkingThread.instance = new WorkingThread();
        }
        return WorkingThread.instance;
    }

    public WorkingThread() {
        messages = new ArrayBlockingQueue<>(20);
        messageHandlers = new ArrayBlockingQueue<>(20);
    }

    public void add(NetworkMessage message, NetworkMessageHandler handler) {
        messages.offer(message);
        messageHandlers.offer(handler);
    }

    @Override
    public void run() {
        while (true) {
            try {
                NetworkMessage message = messages.take();
                NetworkMessageHandler handler = messageHandlers.take();
                handler.handle(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
