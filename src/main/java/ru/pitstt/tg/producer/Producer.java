package ru.pitstt.tg.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.pitstt.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    private final ProducerProperties producerProperties;

    private final KafkaTemplate<Long, User> kafkaTemplate;

    public void produce(User user) {
        kafkaTemplate.send(producerProperties.getInputTopic(), user.getChatId(), user);
    }

    @KafkaListener(topics = "inputTopic", groupId = "input")
    public void consume(User user) {
        log.info(String.format("#### -> Consumed message from inputTopic -> %s", user.toString()));
    }
}
