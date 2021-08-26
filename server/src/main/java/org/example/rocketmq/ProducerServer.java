package org.example.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProducerServer {

    @Value("${spring.cloud.stream.rocketmq.bindings.input2.consumer.tags}")
    private String tag;
    @Value("${spring.cloud.stream.bindings.input.destination}")
    private String topic;

    @Autowired
    DefaultMQProducer producer;

    public Object sendMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("name","jackson");
        map.put("sex","male");
        map.put("from","china");
        map.put("bron","北京");
        Message msg = new Message(topic, tag, UUID.randomUUID().toString().toLowerCase().replace("-",""), JSON.toJSONBytes(map, SerializerFeature.WriteNullStringAsEmpty));
        return producer.send(msg);
    }
}
