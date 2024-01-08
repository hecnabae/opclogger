package com.anymetrik.opclogger.opc.node;

import com.anymetrik.opclogger.model.Measure;
import com.anymetrik.opclogger.opc.subscriptions.IOpcSubscription;
import com.anymetrik.opclogger.utils.DateTimeConverter;
import com.anymetrik.opclogger.utils.VariantConverter;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.time.Instant;
import java.util.logging.Logger;

public class BasicOpcNode extends AbstractOpcNode {

    public BasicOpcNode(String endPointUrl, Double samplingIntervalMs, IOpcSubscription subscription) {
        super(endPointUrl, samplingIntervalMs, subscription);
    }

    // @Override
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        Logger.getLogger(this.getClass().getName()).info("subscription value received: item={" + item.getReadValueId().getNodeId().toString() + "}, value={" + value.getValue() + "}, date={" + value.getServerTime().toString() + "}");
        NodeId nodeId = item.getReadValueId().getNodeId();

        Double val = VariantConverter.convertToDouble(value.getValue());
        Instant serverTime = DateTimeConverter.convertToInstant(value.getServerTime());

        Measure measure = new Measure(1l, val, serverTime);
    }
}
