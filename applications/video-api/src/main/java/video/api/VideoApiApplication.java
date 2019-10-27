package video.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author eduardo.thums
 */
@EnableFeignClients
@SpringBootApplication
public class VideoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoApiApplication.class, args);
	}
}
