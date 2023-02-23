package com.medhead.msemergency;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.medhead.msemergency")
public class CustomProperties {
    
    private String apiUrlHospital;

    private String apiUrlBedAvaibility;

    private String apiUrlDistance;

}
