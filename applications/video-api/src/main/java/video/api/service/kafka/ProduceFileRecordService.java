package video.api.service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import video.api.model.RecordModel;
import video.api.util.HashGenerator;

/**
 * @author eduardo.thums
 */
@Service
public class ProduceFileRecordService {

	private String kafkaTopic;

	private KafkaTemplate<String, RecordModel> kafkaTemplate;

	private HashGenerator hashGenerator;

	public ProduceFileRecordService(@Value("${kafka.topic.ipfs}") String kafkaTopic,
	                                KafkaTemplate<String, RecordModel> kafkaTemplate,
	                                HashGenerator hashGenerator) {
		this.kafkaTopic = kafkaTopic;
		this.kafkaTemplate = kafkaTemplate;
		this.hashGenerator = hashGenerator;
	}

	public void produceFileRecord(Long cameraId, Long startDate, Long endDate, byte[] file) {
		final RecordModel recordModel = new RecordModel(cameraId, startDate, endDate, file, hashGenerator.generateHash(file));

		kafkaTemplate.send(kafkaTopic, recordModel);
	}
}
