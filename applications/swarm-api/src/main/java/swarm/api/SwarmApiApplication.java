package swarm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SwarmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwarmApiApplication.class, args);
	}
}
