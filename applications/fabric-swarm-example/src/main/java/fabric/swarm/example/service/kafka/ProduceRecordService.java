package fabric.swarm.example.service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author eduardo.thums
 */
@Service
public class ProduceRecordService {

	private String defaultVideoPath;

	private String kafkaTopic;

	private KafkaTemplate<String, byte[]> kafkaTemplate;

	public ProduceRecordService(final KafkaTemplate<String, byte[]> kafkaTemplate, @Value("${default.video.path") final String defaultVideoPath, @Value("${kafka.topic}") final String kafkaTopic) {
		this.kafkaTemplate = kafkaTemplate;
		this.defaultVideoPath = defaultVideoPath;
	}

	public void produceRecord() throws IOException {
		final File file = new File(defaultVideoPath);
		final byte[] record = Files.readAllBytes(file.toPath());

		this.kafkaTemplate.send(kafkaTopic, record);
	}
}
