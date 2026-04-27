package com.canteen.backend;

import org.mybatis.spring.annotation.MapperScan; // 必须导入这个
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.canteen.backend.mapper") // 👈 这一行是灵魂！检查这里的字母拼写是否有误
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}