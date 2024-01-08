package com.anymetrik.opclogger.opc.client;

import com.anymetrik.opclogger.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OpcClientManager {
    private static OpcClientManager instance;
    private OpcClientFactory opcClientFactory;
    private HashMap<String, OpcClient> opcClients;

    @Autowired
    private MeasureService measureService;

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

    public MeasureService getMeasureService() {
        return measureService;
    }

    public void setMeasureService(MeasureService measureService) {
        this.measureService = measureService;
    }

    public static OpcClientManager getInstance() {
        if (instance == null) {
            instance = new OpcClientManager();
        }
        return instance;
    }

    private OpcClientManager() {
        this.opcClientFactory = new OpcClientFactory();
        this.opcClients = new HashMap<>();
    }


    public void initOpcClients() throws Exception {
        String serverEndPoint = "opc.tcp://127.0.0.1:49320";
        // REcorrer datos para crear clientes opc
        this.createOpcClient(serverEndPoint);
    }

    public OpcClient createOpcClient(String serverEndPoint) throws Exception {
        OpcClient opcClient = this.opcClientFactory.createNewOpcClient(serverEndPoint, this.measureService);
        opcClient.connect();
        this.opcClients.put(serverEndPoint, opcClient);
        return opcClient;
    }

    public void subscribeNode(String serverEndPoint, String nodeEndPoint, Double samplingInterval) throws Exception {
        OpcClient opcClient = this.opcClients.get(serverEndPoint);
        if (opcClient == null) {
            // error
            return;
        } else {
            opcClient.subscribeBasicNode(nodeEndPoint, samplingInterval, measureService);
        }
    }
}
