package com.anymetrik.opclogger.opc.client;

import com.anymetrik.opclogger.opc.node.OpcNodeFactory;

import java.util.HashMap;

public class OpcClientManager {
    private OpcClientFactory opcClientFactory;
    private HashMap<String, OpcClient> opcClients;

    public OpcClientFactory getOpcClientFactory() {
        return opcClientFactory;
    }

    public void setOpcClientFactory(OpcClientFactory opcClientFactory) {
        this.opcClientFactory = opcClientFactory;
    }

    public HashMap<String, OpcClient> getOpcClients() {
        return opcClients;
    }

    public void setOpcClients(HashMap<String, OpcClient> opcClients) {
        this.opcClients = opcClients;
    }

    public void initOpcClients() throws Exception {
        String serverEndPoint = "opc.tcp://127.0.0.1:49320";
        // REcorrer datos para crear clientes opc
        this.createOpcClient(serverEndPoint);
    }

    private void createOpcClient(String serverEndPoint) throws Exception {
        OpcClient opcClient = this.opcClientFactory.createNewOpcClient(serverEndPoint);
        this.opcClients.put(serverEndPoint, opcClient);
    }

    public void subscribeNode(String serverEndPoint, String nodeEndPoint, Double samplingInterval) throws Exception {
        OpcClient opcClient = this.opcClients.get(serverEndPoint);
        if (opcClient == null) {
            // error
            return;
        } else {
            opcClient.subscribeBasicNode(nodeEndPoint, samplingInterval);
        }
    }
}
