package com.medhead.msauthorization;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.medhead.msauthorization")
public class CustomProperties {

    private String pathToUsersJsonFile;

    private String jwtSecret;

    private String jwtIssuer;
}
