package com.example.demo.config;

import com.example.demo.web.UserController;
import com.example.demo.web.exception.JerseyExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        log.info("JERSEY CONFIGURATION");
        register(UserController.class);
        register(JerseyExceptionMapper.class);
    }
}
