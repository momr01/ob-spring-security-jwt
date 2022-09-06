package ar.com.momr.obspringsecurityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ObSpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObSpringSecurityJwtApplication.class, args);
	}

}
