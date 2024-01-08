package com.anymetrik.opclogger.opc.node;

import com.anymetrik.opclogger.opc.subscriptions.IOpcSubscription;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.ArrayList;
import java.util.List;

public class NotificableOpcNode extends AbstractOpcNode {
    private List<String> channels;

    public NotificableOpcNode(String endPointUrl, Double samplingIntervalMs, IOpcSubscription subscription) {
        super(endPointUrl, samplingIntervalMs, subscription);
        this.channels = new ArrayList<>();
    }

    public NotificableOpcNode(String endPointUrl, Double samplingIntervalMs, IOpcSubscription subscription, List<String> channels) {
        super(endPointUrl, samplingIntervalMs, subscription);
        this.channels = channels;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    // @Override
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        // Push data on channels

    }
}
