package ru.pitstt.tg.stream;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import ru.pitstt.User;
import ru.pitstt.tg.producer.ProducerProperties;
import ru.pitstt.tg.stream.filter.KrasnodarFilter;
import ru.pitstt.tg.stream.filter.MoscowFilter;


@Configuration
@EnableKafkaStreams
public class StreamConfig {

    @Bean
    KStream<Long, User> inputAvroStream(ProducerProperties producerProperties,
                                        StreamsBuilder streamsBuilder) {
        KStream<Long, User> inputKStream = streamsBuilder.stream(producerProperties.getInputTopic());

        inputKStream.filter(MoscowFilter::apply).to(producerProperties.getMoscowTopic());
        inputKStream.filter(KrasnodarFilter::apply).to(producerProperties.getKrasnodarTopic());

        return inputKStream;
    }

    @Bean
    NewTopic moscowTopic(ProducerProperties producerProperties) {
        return TopicBuilder
                .name(producerProperties.getMoscowTopic())
                .partitions(producerProperties.getThreads())
                .build();
    }

    @Bean
    NewTopic krasnodarTopic(ProducerProperties producerProperties) {
        return TopicBuilder
                .name(producerProperties.getKrasnodarTopic())
                .partitions(producerProperties.getThreads())
                .build();
    }
}
