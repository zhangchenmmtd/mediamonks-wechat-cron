package com.mediamonks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MmWechatDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmWechatDomainApplication.class, args);
	}
}
