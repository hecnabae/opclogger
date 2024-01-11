package com.anymetrik.opclogger.opc.client;

import com.anymetrik.opclogger.messaging.MessagePublisher;
import com.anymetrik.opclogger.service.MeasureService;

import java.io.Serializable;

public class OpcClientFactory implements Serializable {
    public OpcClient createNewOpcClient(String serverEndPointUrl, MeasureService measureService) {
        return new OpcClient(serverEndPointUrl, measureService);
    }

    public OpcClient createNewOpcClient(String serverEndPointUrl, MeasureService measureService, MessagePublisher messagePublisher) {
        return new OpcClient(serverEndPointUrl, measureService, messagePublisher);
    }
}
