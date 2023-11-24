package com.anymetrik.opclogger.opc.node;

import com.anymetrik.opclogger.opc.client.OpcClient;

import java.util.List;

public class OpcNodeFactory {
    public static AbstractOpcNode createNode(String nodeEndPoint, Double sampligInterval, String type, List<String> channels) {
        switch (type) {
            case "notificable":
                return new NotificableOpcNode(nodeEndPoint, sampligInterval, channels);
            case "basic":
            default:
                return new BasicOpcNode(nodeEndPoint, sampligInterval);
        }
    }
}
