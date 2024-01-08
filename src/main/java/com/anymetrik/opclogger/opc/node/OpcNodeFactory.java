package com.anymetrik.opclogger.opc.node;

import com.anymetrik.opclogger.opc.subscriptions.IOpcSubscription;

import java.util.List;

public class OpcNodeFactory {
    public static AbstractOpcNode createNode(String nodeEndPoint, Double sampligInterval, String type, IOpcSubscription subscription, List<String> channels) {
        switch (type) {
            case "notificable":
                return new NotificableOpcNode(nodeEndPoint, sampligInterval, subscription, channels);
            case "basic":
            default:
                return new BasicOpcNode(nodeEndPoint, sampligInterval, subscription);
        }
    }
}
