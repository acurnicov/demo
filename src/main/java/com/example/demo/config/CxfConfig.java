package com.example.demo.config;

import com.example.demo.web.UserController;
import com.example.demo.web.exception.ValidationExceptionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Collections;

@Profile("dev")
@Slf4j
@Configuration
@RequiredArgsConstructor
class CxfConfig {
    private final Bus bus;
    private final UserController userController;

    @Bean
    public Server rsServer() {
        log.info("CXF CONFIGURATION");
        final JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setProviders(
                Arrays.asList(
//                        new ValidationExceptionMapper(),
                        new JacksonJsonProvider()
                )
        );
//        endpoint.setInInterceptors(
//                Arrays.<Interceptor<? extends Message>>asList(
//                        new JAXRSBeanValidationInInterceptor()
//                )
//        );
//        endpoint.setOutInterceptors(
//                Arrays.<Interceptor<? extends Message>>asList(
//                        new JAXRSBeanValidationOutInterceptor()
//                )
//        );
        endpoint.setBus(bus);
        endpoint.setAddress("/");
        endpoint.setServiceBeans(Collections.singletonList(userController));

        return endpoint.create();
    }

    @Bean
    public ServletRegistrationBean cxfServlet() {
        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(), "/*");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}