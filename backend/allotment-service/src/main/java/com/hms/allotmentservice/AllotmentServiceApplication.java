package com.hms.allotmentservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Automatically registers this app with our active Eureka registry ring
public class AllotmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AllotmentServiceApplication.class, args);
    }
}
