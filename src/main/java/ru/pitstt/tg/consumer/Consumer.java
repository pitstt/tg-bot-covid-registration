package ru.pitstt.tg.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.pitstt.Message;
import ru.pitstt.tg.ExampleBot;

@Slf4j
@Service
@RequiredArgsConstructor
public class Consumer {

    private final ExampleBot exampleBot;

    @KafkaListener(topics = "${app.result-topic}", groupId = "result")
    public void consume(Message message) {
        log.info(String.format("#### -> Consumed message from resultTopic-> %s", message.toString()));
        exampleBot.sendMessage(message.getChatId(), message.getText());
    }
}
