package application.config;

import application.stateMachine.SimpleStateMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateMachineConfig {

    @Bean
    public SimpleStateMachine getSimpleStateMachine() {
        return new SimpleStateMachine();
    }
}