package com.example.netflix.config;

import com.example.netflix.security.IpAddressFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<IpAddressFilter> ipAddressFilterRegistration(IpAddressFilter filter) {
        FilterRegistrationBean<IpAddressFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(1); // Set the order of the filter
        return registration;
    }
}

