package fabric.swarm.example.controller.kafka;

import fabric.swarm.example.service.kafka.ProduceRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

	private ProduceRecordService produceRecordService;

	public KafkaController(ProduceRecordService produceRecordService) {
		this.produceRecordService = produceRecordService;
	}

	@PostMapping
	public void produceRecord() throws IOException {
		produceRecordService.produceRecord();
	}
}
