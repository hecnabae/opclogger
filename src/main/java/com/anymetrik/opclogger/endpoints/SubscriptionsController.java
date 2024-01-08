package com.anymetrik.opclogger.endpoints;

import com.anymetrik.opclogger.model.OpcNode;
import com.anymetrik.opclogger.opc.client.OpcClient;
import com.anymetrik.opclogger.opc.client.OpcClientManager;
import com.anymetrik.opclogger.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionsController {
    @Autowired
    private MeasureService measureService;

    @PostMapping
    public ResponseEntity<OpcNode> subscribeNode(@RequestBody OpcNode node) {
        try {
            String serverEndPoint = node.getServerEndPoint();
            String nodeEndPoint = node.getNodeEndPoint();
            Double samplinIntervalMillis = node.getSamplinIntervalMillis();

            OpcClientManager clientManager = OpcClientManager.getInstance();
            HashMap<String, OpcClient> opcClients = clientManager.getOpcClients();
            OpcClient opcClient = opcClients.get(serverEndPoint);

            if (opcClient != null) {
                opcClient.subscribeBasicNode(nodeEndPoint, samplinIntervalMillis, measureService);
            } else {
                clientManager.createOpcClient(serverEndPoint);
            }


        } catch (Exception e) {

        }
        return ResponseEntity.ok(node);
    }
}
