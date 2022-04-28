package application.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

@Configuration
public class LoggerConfig {
    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger("DocumentsApplication");
    }
}