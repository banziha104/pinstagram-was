package com.pinstagram.contents.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="server-address")
@Getter
@Setter
public class ServerAddressConfig {
    private String geoServerAddress;
    private String contentsServerAddress;
    private String authServerAddress;
}
