package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.spider.db")
public class BootSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootSpiderApplication.class, args);
	}

}

