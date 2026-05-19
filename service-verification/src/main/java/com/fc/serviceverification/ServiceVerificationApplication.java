package com.fc.serviceverification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVerificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVerificationApplication.class, args);
    }
}
