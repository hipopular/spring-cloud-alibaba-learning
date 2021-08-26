package org.example.rocketmq;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServer {

    @StreamListener(Sink.INPUT)
    public void receiveInput1(String receiveMsg) {
        System.out.println("input receive: " + receiveMsg);
    }
}
