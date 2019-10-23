package logger.api.service.kafka;

import logger.api.model.LogRequestModel;
import logger.api.service.log.SaveLogService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class ConsumeLogRequestModelService {

	private SaveLogService saveLogService;

	public ConsumeLogRequestModelService(SaveLogService saveLogService) {
		this.saveLogService = saveLogService;
	}

	@KafkaListener(topics = "${kafka.topic.blockchain}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, LogRequestModel> record) throws Exception {
		saveLogService.saveLogEntity(record.value());
	}
}
