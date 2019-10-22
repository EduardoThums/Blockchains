package fabric.api.service;

import fabric.api.model.RecordModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class ConsumeRecordModelService {

	@KafkaListener(topics = "${kafka.topic.ipfs}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, RecordModel> record) {
		log.info("Storage hash: {}", record.value().getStorageHash());
	}
}
