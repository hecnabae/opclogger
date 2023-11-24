package com.anymetrik.opclogger;

import com.anymetrik.opclogger.opc.client.OpcClientManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class OpcloggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpcloggerApplication.class, args);
        // Suscribir servidor opc
        OpcClientManager clientManager = new OpcClientManager();
        try {
            clientManager.initOpcClients();
        } catch (Exception e) {
            Logger.getLogger(OpcloggerApplication.class.getName()).severe(e.getMessage());
        }
    }

}
