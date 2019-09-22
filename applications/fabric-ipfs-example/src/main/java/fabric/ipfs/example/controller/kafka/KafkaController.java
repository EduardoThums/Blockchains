package fabric.ipfs.example.controller.kafka;

import fabric.ipfs.example.service.kafka.ProduceRecordService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	private ProduceRecordService produceRecordService;

	public KafkaController(ProduceRecordService produceRecordService) {
		this.produceRecordService = produceRecordService;
	}

	@PostMapping("/camera/{cameraId}")
	public void produceRecord(@PathVariable("cameraId") Long cameraId, @RequestParam("videoPath") String videoPath) throws IOException {
		produceRecordService.produceRecord(cameraId, videoPath);
	}
}
