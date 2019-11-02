package bigchaindb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BigchaindbApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigchaindbApiApplication.class, args);
	}

}
