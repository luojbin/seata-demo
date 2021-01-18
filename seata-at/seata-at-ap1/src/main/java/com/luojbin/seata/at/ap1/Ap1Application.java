package com.luojbin.seata.at.ap1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages={"com.luojbin.seata.at"})
@EnableEurekaClient
@EnableFeignClients(basePackages={"com.luojbin.seata.at"})
@MapperScan("com.luojbin.seata.at")
public class Ap1Application {
    public static void main(String[] args) {
        SpringApplication.run(Ap1Application.class, args);
    }
}
