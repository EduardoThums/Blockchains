package fabric.swarm.example.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author eduardo.thums
 */
@Configuration
public class SwarmFeignConfig {

	@Bean
	public Encoder feignFormEncoder() {
		return new SpringFormEncoder();
	}
}
