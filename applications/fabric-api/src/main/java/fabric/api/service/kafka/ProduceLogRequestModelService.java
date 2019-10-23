package fabric.api.service.kafka;

import fabric.api.model.LogRequestModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author eduardo.thums
 */
@Service
public class ProduceLogRequestModelService {

	private String kafkaTopic;

	private KafkaTemplate<String, LogRequestModel> kafkaTemplate;

	public ProduceLogRequestModelService(@Value("${kafka.topic.logger}") String kafkaTopic,
	                                     KafkaTemplate<String, LogRequestModel> kafkaTemplate) {
		this.kafkaTopic = kafkaTopic;
		this.kafkaTemplate = kafkaTemplate;
	}

	void produceLogRequestModel(Instant startDate, Instant endDate) {
		kafkaTemplate.send(kafkaTopic, new LogRequestModel(startDate, endDate));
	}
}
