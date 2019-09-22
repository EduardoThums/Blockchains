package fabric.ipfs.example.service.kafka;

import fabric.ipfs.example.model.RecordModel;
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

	private String kafkaTopic;

	private KafkaTemplate<String, RecordModel> kafkaTemplate;

	public ProduceRecordService(@Value("${kafka.topic}") String kafkaTopic,
								KafkaTemplate<String, RecordModel> kafkaTemplate) {
		this.kafkaTopic = kafkaTopic;
		this.kafkaTemplate = kafkaTemplate;
	}

	public void produceRecord(Long cameraId, String videoPath) throws IOException {
		final File file = new File(videoPath);
		final byte[] value = Files.readAllBytes(file.toPath());
		final RecordModel record = new RecordModel(cameraId, value);

		this.kafkaTemplate.send(kafkaTopic, record);
	}
}
