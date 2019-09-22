package fabric.ipfs.example.service.kafka;

import fabric.ipfs.example.model.RecordModel;
import fabric.ipfs.example.service.ipfs.UploadFileService;
import fabric.ipfs.example.util.HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class ConsumeRecordService {

	private HashGenerator hashGenerator;

	private UploadFileService uploadFileService;

	public ConsumeRecordService(HashGenerator hashGenerator, UploadFileService uploadFileService) {
		this.hashGenerator = hashGenerator;
		this.uploadFileService = uploadFileService;
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(final ConsumerRecord<String, RecordModel> record) throws IOException {
		final String storageHash = uploadFileService.uploadFile(record.value().getVideoRecord());
		final String contentHash = hashGenerator.generate(record.value().getVideoRecord());

		log.info("Storage hash {}", storageHash);
		log.info("Content hash {}", contentHash);
	}
}
