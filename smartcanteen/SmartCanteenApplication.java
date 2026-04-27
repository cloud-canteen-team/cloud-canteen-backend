package com.s008.smartcanteen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.s008.smartcanteen.mapper") // 必须指向你存放那6个接口的包路径
public class SmartCanteenApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartCanteenApplication.class, args);
    }
}