package com.anymetrik.opclogger.model;

public class NotificableOpcNode extends OpcNode {
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
