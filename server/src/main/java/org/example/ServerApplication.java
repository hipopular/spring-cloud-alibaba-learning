package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ServerApplication
 */
@EnableBinding({ Source.class, Sink.class })
@EnableDiscoveryClient
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
    }

    @RestController
    static class ServerController{

        @GetMapping("/index/{s}")
        public String index(@PathVariable String s) {
            return "Hello Nacos Discovery " + s;
        }
    }

    @Component
    public class ProducerRunner implements CommandLineRunner {

        @Value("${spring.cloud.stream.rocketmq.bindings.input2.consumer.tags}")
        private String tag;
        @Value("${spring.cloud.stream.bindings.input.group}")
        private String group;
        @Value("${spring.cloud.stream.bindings.input.destination}")
        private String topic;
        @Value("${spring.cloud.stream.rocketmq.binder.name-server}")
        private String namesrvAddr;

        @Override
        public void run(String... args) throws Exception {
            DefaultMQProducer producer = new DefaultMQProducer(group);
            producer.setNamesrvAddr(namesrvAddr);
            producer.start();
            Map<String,Object> map = new HashMap<>();
            map.put("name","jackson");
            map.put("sex","male");
            map.put("from","china");
            map.put("bron","北京");
            Message msg = new Message(topic, tag, UUID.randomUUID().toString().toLowerCase().replace("-",""), JSON.toJSONBytes(map, SerializerFeature.WriteNullStringAsEmpty));
            producer.send(msg);
        }
    }

    @Service
    public class ReceiveService {

        @StreamListener(Sink.INPUT)
        public void receiveInput1(String receiveMsg) {
            System.out.println("input receive: " + receiveMsg);
        }

    }

}
