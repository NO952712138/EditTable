package com.tfx.cisdi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.tfx.*"})
@MapperScan("com.tfx.dao")
public class CisdiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CisdiApplication.class, args);
	}
}
