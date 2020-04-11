package com.kanbanedchain.lianatasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LianatasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(LianatasksApplication.class, args);
	}

}
