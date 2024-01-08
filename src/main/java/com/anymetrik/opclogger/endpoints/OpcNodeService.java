package com.anymetrik.opclogger.endpoints;

import com.anymetrik.opclogger.opc.client.OpcClient;
import com.anymetrik.opclogger.opc.client.OpcClientManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OpcNodeService {
    public void subscribeNode(String serverEndPoint, String nodeEndPoint, Double samplinIntervalMillis) {
        try {
            OpcClientManager clientManager = OpcClientManager.getInstance();
            HashMap<String, OpcClient> opcClients = clientManager.getOpcClients();
            OpcClient opcClient = opcClients.get(serverEndPoint);
            if (opcClient != null) {
                opcClient.connect();
            } else {
                clientManager.createOpcClient(serverEndPoint);
            }
        } catch (Exception e) {

        }
    }
}
