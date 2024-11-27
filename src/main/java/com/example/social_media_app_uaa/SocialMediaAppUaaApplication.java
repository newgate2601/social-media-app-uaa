package com.example.social_media_app_uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// https://chatgpt.com/c/67357cc8-19fc-8010-8034-dd41e8ed0ec6
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class SocialMediaAppUaaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialMediaAppUaaApplication.class, args);
	}
}
