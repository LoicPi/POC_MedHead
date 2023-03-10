package com.medhead.mshospital;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.medhead.mshospital")
public class CustomProperties {

    private String apiUrlBedAvailable;

    private String pathToHospitalsJsonFile;

    private String pathToSpecialtiesJsonFile;

}
