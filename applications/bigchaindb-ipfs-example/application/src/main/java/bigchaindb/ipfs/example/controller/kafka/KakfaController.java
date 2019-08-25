package bigchaindb.ipfs.example.controller.kafka;

import bigchaindb.ipfs.example.service.kafka.KafkaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/kafka")
public class KakfaController {

	private KafkaService kafkaService;

	public KakfaController(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

	@PostMapping("/producer")
	public void produce() throws IOException {
		this.kafkaService.startProducer();
	}
}
