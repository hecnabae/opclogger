package com.anymetrik.opclogger.opc.node;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

public class BasicOpcNode extends AbstractOpcNode {
    public BasicOpcNode(String endPointUrl, Double samplingIntervalMs) {
        super(endPointUrl, samplingIntervalMs);
    }

    @Override
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value) {

    }
}
