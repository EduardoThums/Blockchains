package bigchaindb.ipfs.example;

import bigchaindb.ipfs.example.config.BigchaindbConfig;
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
	public void initBigchainDbConfig() {
		BigchainDbConfigBuilder.baseUrl(BigchaindbConfig.BASE_URL.getValue()).setup();
	}
}
