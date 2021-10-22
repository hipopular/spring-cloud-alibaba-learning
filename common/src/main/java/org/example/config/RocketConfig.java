package org.example.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketConfig {

    @Value("${spring.cloud.stream.bindings.input.group}")
    private String group;

    @Value("${spring.cloud.stream.rocketmq.binder.name-server}")
    private String addr;

    @Bean
    public DefaultMQProducer producer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(addr);
        producer.start();
        return producer;
    }
}
