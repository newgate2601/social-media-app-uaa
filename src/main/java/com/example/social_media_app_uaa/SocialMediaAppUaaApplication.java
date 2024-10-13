package com.example.social_media_app_uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SocialMediaAppUaaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaAppUaaApplication.class, args);
	}

}
