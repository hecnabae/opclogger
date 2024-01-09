package com.anymetrik.opclogger.config;

import com.anymetrik.opclogger.messaging.MessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfifg {

    @Configuration
    public class RedisConfig {

        @Bean
        RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                MessageListenerAdapter listenerAdapter) {
            RedisMessageListenerContainer container = new RedisMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.addMessageListener(listenerAdapter, new PatternTopic("myTopic"));
            return container;
        }

        @Bean
        MessageListenerAdapter listenerAdapter(MessageListener receiver) {
            return new MessageListenerAdapter(receiver, "onReceiveMessage");
        }

        @Bean
        MessageListener receptor() {
            return new MessageListener();
        }
    }

}
