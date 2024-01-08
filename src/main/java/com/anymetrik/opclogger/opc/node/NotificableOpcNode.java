package com.anymetrik.opclogger.opc.node;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NotificableOpcNode extends AbstractOpcNode {
    private List<String> channels;

    public NotificableOpcNode(String endPointUrl, Double samplingIntervalMs) {
        super(endPointUrl, samplingIntervalMs);
        this.channels = new ArrayList<>();
    }

    public NotificableOpcNode(String endPointUrl, Double samplingIntervalMs, List<String> channels) {
        super(endPointUrl, samplingIntervalMs);
        this.channels = channels;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    @Override
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        // Push data on channels

    }
}
