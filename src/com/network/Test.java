package com.network;

import com.network.client.Client;
import com.network.common.MessageType;
import com.network.server.Server;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        /*new Thread(() -> {
            new Server(6060);
        }).start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < 1; i++) {
            new Thread(() -> {
                new Client(6060);
            }).start();
        }*/
    }
}
