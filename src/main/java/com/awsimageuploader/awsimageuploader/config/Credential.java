package com.awsimageuploader.awsimageuploader.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties("aws")
@Getter
@Setter
@Component
@PropertySource("classpath:credentials.properties")
public class Credential {
    private String key;
    private String value;
}
