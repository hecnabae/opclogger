package com.anymetrik.opclogger.opc.node;

import com.anymetrik.opclogger.opc.subscriptions.IOpcSubscription;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;


public abstract class AbstractOpcNode implements Serializable {
    protected String endPointUrl;
    protected Double samplingIntervalMs;
    private UaSubscription uaSubscription;
    private IOpcSubscription subscription;

    public String getEndPointUrl() {
        return endPointUrl;
    }

    public void setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
    }

    public Double getSamplingIntervalMs() {
        return samplingIntervalMs;
    }

    public void setSamplingIntervalMs(Double samplingIntervalMs) {
        this.samplingIntervalMs = samplingIntervalMs;
    }

    public UaSubscription getUaSubscription() {
        return uaSubscription;
    }

    public void setUaSubscription(UaSubscription uaSubscription) {
        this.uaSubscription = uaSubscription;
    }

    public AbstractOpcNode(String endPointUrl, Double samplingIntervalMs, IOpcSubscription subscription) {
        this.endPointUrl = endPointUrl;
        this.samplingIntervalMs = samplingIntervalMs;
        this.subscription = subscription;
    }

    public void subscribe(OpcUaClient opcUaClient) throws Exception {
        if (opcUaClient == null) {
            throw new Exception("No estás conectado al servidor OPC. Primero, conecta al servidor OPC.");
        }

        // create a subscription
        this.uaSubscription = opcUaClient.getSubscriptionManager().createSubscription(this.samplingIntervalMs).get();

        List<MonitoredItemCreateRequest> requests = new ArrayList<>();
        NodeId node = NodeId.parse(this.endPointUrl);
        ReadValueId readValueId = new ReadValueId(
                node,
                AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE
        );
        // IMPORTANT: client handle must be unique per item within the context of a subscription.
        // You are not required to use the UaSubscription's client handle sequence; it is provided as a convenience.
        // Your application is free to assign client handles by whatever means necessary.
        UInteger clientHandle = this.uaSubscription.nextClientHandle();

        MonitoringParameters parameters = new MonitoringParameters(
                clientHandle,
                1000.0,     // sampling interval
                null,       // filter, null means use default
                uint(10),   // queue size
                true        // discard oldest
        );

        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId,
                MonitoringMode.Reporting,
                parameters
        );
        requests.add(request);


        // when creating items in MonitoringMode.Reporting this callback is where each item needs to have its
        // value/event consumer hooked up. The alternative is to create the item in sampling mode, hook up the
        // consumer after the creation call completes, and then change the mode for all items to reporting.
        UaSubscription.ItemCreationCallback onItemCreated =
                (item, id) -> item.setValueConsumer(subscription);

        List<UaMonitoredItem> items = this.uaSubscription.createMonitoredItems(
                TimestampsToReturn.Both,
                requests,
                onItemCreated
        ).get();

        for (
                UaMonitoredItem item : items) {
            if (item.getStatusCode().isGood()) {
                Logger.getLogger(this.getClass().getName()).info("item created for nodeId={" + item.getReadValueId().getNodeId() + "}");
            } else {
                Logger.getLogger(this.getClass().getName()).warning(
                        "failed to create item for nodeId={" + item.getReadValueId().getNodeId().toString() + "} (status={" + item.getStatusCode() + "})");
            }
        }

    }

    public void unsubscribe(OpcUaClient opcUaClient) throws Exception {
        if (opcUaClient == null) {
            throw new Exception("No hay ninguna suscripción con el cliente.");
        }
        if (this.uaSubscription == null) {
            throw new Exception("No se ha creado ninguna suscripción para el nodo " + endPointUrl);
        }

        opcUaClient.getSubscriptionManager().deleteSubscription(this.uaSubscription.getSubscriptionId());
        this.uaSubscription = null;
    }
}

