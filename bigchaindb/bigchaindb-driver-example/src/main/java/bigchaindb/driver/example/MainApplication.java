package bigchaindb.driver.example;

import bigchaindb.driver.example.config.Config;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@PostConstruct
	public void init() {
		BigchainDbConfigBuilder.baseUrl(Config.BASE_URL.getValue()).setup();
	}
}
