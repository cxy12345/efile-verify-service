package com.verify.efileverifyservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.verify.efileverifyservice.mapper"})
public class EfileVerifyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfileVerifyServiceApplication.class, args);
    }

}
