package com.anymetrik.opclogger.opc.subscriptions;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

public interface IOpcSubscription extends UaMonitoredItem.ValueConsumer {
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value);
}
