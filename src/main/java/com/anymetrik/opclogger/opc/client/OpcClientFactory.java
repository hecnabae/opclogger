package com.anymetrik.opclogger.opc.client;

import java.io.Serializable;

public class OpcClientFactory implements Serializable {
    public OpcClient createNewOpcClient(String serverEndPointUrl) {
        return new OpcClient(serverEndPointUrl);
    }
}
