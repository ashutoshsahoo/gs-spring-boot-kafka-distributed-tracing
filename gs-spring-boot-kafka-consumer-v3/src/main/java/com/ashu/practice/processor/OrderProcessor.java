package com.ashu.practice.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProcessor {

    @KafkaListener(id = "orders", topics = "orders", groupId = "${spring.application.name:consumer-v3}")
    public void onEvent(ConsumerRecord<Long, String> consumerRecord) {
        log.info("Received message with Key:{} , value: {}", consumerRecord.key(), consumerRecord.value());
    }
}