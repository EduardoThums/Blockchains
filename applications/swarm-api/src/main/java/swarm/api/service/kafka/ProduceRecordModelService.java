package swarm.api.service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import swarm.api.model.RecordModel;

/**
 * @author eduardo.thums
 */
@Service
public class ProduceRecordModelService {

	private String kafkaTopic;

	private KafkaTemplate<String, RecordModel> kafkaTemplate;

	public ProduceRecordModelService(@Value("${kafka.topic.blockchain}") String kafkaTopic,
	                                 KafkaTemplate<String, RecordModel> kafkaTemplate) {
		this.kafkaTopic = kafkaTopic;
		this.kafkaTemplate = kafkaTemplate;
	}

	void produceRecord(RecordModel recordModel) {
		kafkaTemplate.send(kafkaTopic, recordModel);
	}
}
