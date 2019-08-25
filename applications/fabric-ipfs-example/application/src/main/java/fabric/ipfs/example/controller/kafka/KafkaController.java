package fabric.ipfs.example.controller.kafka;

import fabric.ipfs.example.service.kafka.KafkaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	private KafkaService kafkaService;

	public KafkaController(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

	@PostMapping("/produce")
	public void produce() throws IOException, ExecutionException, InterruptedException {
		kafkaService.startProducer();
	}
}
