package fabric.ipfs.example.service.kafka;

import fabric.ipfs.example.model.RecordModel;
import fabric.ipfs.example.util.HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class ConsumeRecordService {

	private HashGenerator hashGenerator;

	public ConsumeRecordService(HashGenerator hashGenerator) {
		this.hashGenerator = hashGenerator;
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(final ConsumerRecord<String, RecordModel> record) {
		final String contentHash = hashGenerator.generate(record.value().getVideoRecord());

		log.info("Content hash {}", contentHash);
	}
}
