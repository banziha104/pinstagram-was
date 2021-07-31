package com.pinstagram.geo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class ApiKeyConfig {
    @Value("${googleApiKey}")
    private String googleApiKey;
}
