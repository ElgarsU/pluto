package eu.skalaengineering.pluto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlutoApplication.class, args);
	}
}
