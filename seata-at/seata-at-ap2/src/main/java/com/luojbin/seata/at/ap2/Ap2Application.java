package com.luojbin.seata.at.ap2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.luojbin.seata.at"})
@EnableEurekaClient
@EnableFeignClients(basePackages={"com.luojbin.seata.at"})
@MapperScan("com.luojbin.seata.at")
@EnableScheduling
public class Ap2Application {
    public static void main(String[] args) {
        SpringApplication.run(Ap2Application.class, args);
    }
}
