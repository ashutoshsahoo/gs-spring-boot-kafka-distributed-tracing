package com.ashu.practice.controller;


import com.ashu.practice.dto.OrderDto;
import com.ashu.practice.service.OrderGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v2/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final KafkaTemplate<Long, OrderDto> template;
    private final  OrderGeneratorService orderGeneratorService;
    private static final AtomicLong id = new AtomicLong();
    public static final String TOPIC_ORDERS = "orders";
    @PostMapping
    public OrderDto create(@RequestBody OrderDto order) {
        order.setId(id.incrementAndGet());
        template.send(TOPIC_ORDERS, order.getId(), order);
        log.info("Sent: {}", order);
        return order;
    }

    @PostMapping("/generate")
    public void create(@RequestParam(name = "count", defaultValue = "1") int count) {
        orderGeneratorService.generate(count);
    }

}
