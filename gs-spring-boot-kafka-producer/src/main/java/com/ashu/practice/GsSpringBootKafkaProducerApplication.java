package com.ashu.practice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class GsSpringBootKafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsSpringBootKafkaProducerApplication.class, args);
	}

	@Bean
	public NewTopic topicOrder() {
		return TopicBuilder
				.name("orders")
				.partitions(6)
				.replicas(3)
				.build();
	}
}
