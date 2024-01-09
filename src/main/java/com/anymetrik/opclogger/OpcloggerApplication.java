package com.anymetrik.opclogger;

import com.anymetrik.opclogger.opc.client.OpcClientManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

import java.util.logging.Logger;

@SpringBootApplication
@Cacheable
public class OpcloggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpcloggerApplication.class, args);
        // Suscribir servidor opc
        OpcClientManager clientManager = OpcClientManager.getInstance();
        try {
            clientManager.initOpcClients();
        } catch (Exception e) {
            Logger.getLogger(OpcloggerApplication.class.getName()).severe(e.getMessage());
        }
    }

}
