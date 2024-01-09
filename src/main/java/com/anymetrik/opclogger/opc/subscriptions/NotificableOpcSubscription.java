package com.anymetrik.opclogger.opc.subscriptions;

import com.anymetrik.opclogger.messaging.MessagePublisher;
import com.anymetrik.opclogger.model.Measure;
import com.anymetrik.opclogger.opc.node.AbstractOpcNode;
import com.anymetrik.opclogger.service.MeasureService;
import com.anymetrik.opclogger.utils.DateTimeConverter;
import com.anymetrik.opclogger.utils.VariantConverter;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.time.Instant;
import java.util.logging.Logger;

public class NotificableOpcSubscription implements IOpcSubscription {
    private MeasureService measureService;

    private MessagePublisher messagePublisher;

    private AbstractOpcNode opcNode;

    public AbstractOpcNode getOpcNode() {
        return opcNode;
    }

    public void setOpcNode(AbstractOpcNode opcNode) {
        this.opcNode = opcNode;
    }

    public NotificableOpcSubscription(MeasureService measureService, MessagePublisher messagePublisher, AbstractOpcNode opcNode) {
        this.measureService = measureService;
        this.messagePublisher = messagePublisher;
        this.opcNode = opcNode;
    }

    @Override
    public void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        String message = "subscription value received: item={" + item.getReadValueId().getNodeId().toString() + "}, value={" + value.getValue() + "}, date={" + value.getServerTime().toString() + "}";
        Logger.getLogger(this.getClass().getName()).info(message);
        NodeId nodeId = item.getReadValueId().getNodeId();

        Double val = VariantConverter.convertToDouble(value.getValue());
        Instant serverTime = DateTimeConverter.convertToInstant(value.getServerTime());

        Measure measure = new Measure(1l, val, serverTime);
        this.measureService.addMeasure(measure);
        this.messagePublisher.publishMessage(message);
    }

    @Override
    public void onValueArrived(UaMonitoredItem item, DataValue value) {
        String message = "subscription value received: item={" + item.getReadValueId().getNodeId().toString() + "}, value={" + value.getValue() + "}, date={" + value.getServerTime().toString() + "}";
        Logger.getLogger(this.getClass().getName()).info(message);
        NodeId nodeId = item.getReadValueId().getNodeId();

        Double val = VariantConverter.convertToDouble(value.getValue());
        Instant serverTime = DateTimeConverter.convertToInstant(value.getServerTime());

        Measure measure = new Measure(1l, val, serverTime);
        this.measureService.addMeasure(measure);
        this.messagePublisher.publishMessage(message);
    }
}
