package com.malajmi.elm_wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ElmWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElmWalletApplication.class, args);
	}

}
