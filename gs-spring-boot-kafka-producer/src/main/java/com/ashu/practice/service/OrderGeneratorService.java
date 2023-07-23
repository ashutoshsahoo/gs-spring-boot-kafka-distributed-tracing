package com.ashu.practice.service;

import com.ashu.practice.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderGeneratorService {

    public static final String SOURCE = "order-v2";
    public static final String TOPIC_ORDERS = "orders";
    private final KafkaTemplate<Long, String> template;
    private final ObjectMapper mapper;
    private static final Random SECURE_RANDOM = new Random();

    public void generate(int count) {
        final AtomicLong id = new AtomicLong();
        for (int i = 0; i < count; i++) {
            int x = SECURE_RANDOM.nextInt(5) + 1;
            OrderDto o = OrderDto.builder()
                    .id(id.incrementAndGet())
                    .customerId(SECURE_RANDOM.nextLong(100) + 1)
                    .productId(SECURE_RANDOM.nextLong(100) + 1)
                    .status("NEW")
                    .price(100 * x)
                    .productCount(x)
                    .source(SOURCE)
                    .build();
            String data = null;
            try {
                data = mapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                log.error("Error occurred while converting to json", e);
            }
            template.send(TOPIC_ORDERS, o.getId(), data);
            log.info("Generated order:{}", data);
        }
    }

}
