package com.anymetrik.opclogger.opc.subscriptions;

import com.anymetrik.opclogger.model.Measure;
import com.anymetrik.opclogger.service.MeasureService;
import com.anymetrik.opclogger.utils.DateTimeConverter;
import com.anymetrik.opclogger.utils.VariantConverter;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.time.Instant;
import java.util.logging.Logger;

public class OpcSubscription implements IOpcSubscription {
    private MeasureService measureService;

    public OpcSubscription(MeasureService measureService) {
        this.measureService = measureService;
    }

    @Override
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        Logger.getLogger(this.getClass().getName()).info("subscription value received: item={" + item.getReadValueId().getNodeId().toString() + "}, value={" + value.getValue() + "}, date={" + value.getServerTime().toString() + "}");
        NodeId nodeId = item.getReadValueId().getNodeId();

        Double val = VariantConverter.convertToDouble(value.getValue());
        Instant serverTime = DateTimeConverter.convertToInstant(value.getServerTime());

        Measure measure = new Measure(1l, val, serverTime);
        this.measureService.addMeasure(measure);
    }

    @Override
    public void onValueArrived(UaMonitoredItem item, DataValue value) {
        Logger.getLogger(this.getClass().getName()).info("subscription value received: item={" + item.getReadValueId().getNodeId().toString() + "}, value={" + value.getValue() + "}, date={" + value.getServerTime().toString() + "}");
        NodeId nodeId = item.getReadValueId().getNodeId();

        Double val = VariantConverter.convertToDouble(value.getValue());
        Instant serverTime = DateTimeConverter.convertToInstant(value.getServerTime());

        Measure measure = new Measure(1l, val, serverTime);
        this.measureService.addMeasure(measure);
    }
}
