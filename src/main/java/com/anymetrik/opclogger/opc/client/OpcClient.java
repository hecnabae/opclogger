package com.anymetrik.opclogger.opc.client;

import com.anymetrik.opclogger.opc.node.AbstractOpcNode;
import com.anymetrik.opclogger.opc.node.OpcNodeFactory;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class OpcClient implements Serializable {
    private OpcUaClient opcUaClient;
    private UaClient uaClient;
    private String serverEndpoint;

    private HashMap<String, AbstractOpcNode> nodes;

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }

    public void setOpcUaClient(OpcUaClient opcUaClient) {
        this.opcUaClient = opcUaClient;
    }

    public UaClient getUaClient() {
        return uaClient;
    }

    public void setUaClient(UaClient uaClient) {
        this.uaClient = uaClient;
    }

    public String getServerEndpoint() {
        return serverEndpoint;
    }

    public void setServerEndpoint(String serverEndpoint) {
        this.serverEndpoint = serverEndpoint;
    }

    public HashMap<String, AbstractOpcNode> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<String, AbstractOpcNode> nodes) {
        this.nodes = nodes;
    }

    public OpcClient(String serverEndpoint) {
        this.serverEndpoint = serverEndpoint;
        this.nodes = new HashMap<>();
    }

    public void connect() throws Exception {
        // Crea un cliente OPC UA
        opcUaClient = OpcUaClient.create(
                serverEndpoint,
                endpoints ->
                        endpoints.stream()
                                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getUri()))
                                .findFirst(),
                configBuilder ->
                        configBuilder.build()
        );

        // Conecta al servidor OPC UA
        this.uaClient = opcUaClient.connect().get();
    }

    public void disconnect() throws Exception {
        try {
            if (opcUaClient != null) {
                this.deleSubscriptions();
                opcUaClient.disconnect().get();
            } else {
                throw new Exception("No estás conectado a un servidor OPC UA.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al desconectar del servidor OPC UA: " + e.getMessage());
        }
    }

    private void deleSubscriptions() throws Exception {
        for (AbstractOpcNode node : this.nodes.values()) {
            node.unsubscribe(this.opcUaClient);
        }
    }

    public void subscribeBasicNode(String nodeEndPoint, Double samplingInterval) throws Exception {
        AbstractOpcNode basicNode = this.nodes.get(nodeEndPoint);
        if (basicNode == null) {
            basicNode = OpcNodeFactory.createNode(nodeEndPoint, samplingInterval, "basic", new ArrayList<>());
            this.nodes.put(nodeEndPoint, basicNode);
        }
        if (basicNode != null && basicNode.getUaSubscription() != null) {
            basicNode.subscribe(this.opcUaClient);
        }
    }
}
