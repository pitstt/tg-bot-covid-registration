package ru.pitstt.tg.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableConfigurationProperties(ProducerProperties.class)
public class ProducerConfig {

    @Bean
    NewTopic inputTopic(ProducerProperties producerProperties) {
        return TopicBuilder
                .name(producerProperties.getInputTopic())
                .partitions(producerProperties.getThreads())
                .build();
    }
}
