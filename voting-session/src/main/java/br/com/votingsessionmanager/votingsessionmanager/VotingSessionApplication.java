
package br.com.votingsessionmanager.votingsessionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.votingsessionmanager")
public class VotingSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingSessionApplication.class, args);
	}

}