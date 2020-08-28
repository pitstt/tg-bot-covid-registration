package ru.pitstt.tg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "tg")
public class TelegramProperties {

    @NotBlank
    private final String token;

    @NotBlank
    private final String name;

}
