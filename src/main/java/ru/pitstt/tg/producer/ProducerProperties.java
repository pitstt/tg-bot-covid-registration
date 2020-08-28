package ru.pitstt.tg.producer;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "app")
public class ProducerProperties {

    @NotBlank
    private final String inputTopic;

    @NotBlank
    private final String moscowTopic;

    @NotBlank
    private final String krasnodarTopic;

    @Positive
    private final Integer threads;

}
