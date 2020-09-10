package com.hxzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hxzy.mapper.hr")
public class HrApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class,args);
    }
}
