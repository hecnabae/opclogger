package com.anymetrik.opclogger.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void publishMessage(String mensaje) {
        redisTemplate.convertAndSend("myTopic", mensaje);
    }
}
