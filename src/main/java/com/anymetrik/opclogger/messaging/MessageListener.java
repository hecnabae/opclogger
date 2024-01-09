package com.anymetrik.opclogger.messaging;

public class MessageListener {
    public void onReceiveMessage(String message) {
        System.out.println("Message received: " + message);
    }
}
