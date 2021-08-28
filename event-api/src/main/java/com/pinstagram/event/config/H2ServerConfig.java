package com.pinstagram.event.config;


import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("test")
public class H2ServerConfig {
    @Bean
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer()
                .start();
    }
}