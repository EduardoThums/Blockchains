package fabric.ipfs.example.service.kafka;

import fabric.ipfs.example.model.RecordModel;
import fabric.ipfs.example.service.fabric.CreateTransactionService;
import fabric.ipfs.example.service.ipfs.UploadFileService;
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

	private UploadFileService uploadFileService;

	private CreateTransactionService createTransactionService;

	public ConsumeRecordService(HashGenerator hashGenerator, UploadFileService uploadFileService, CreateTransactionService createTransactionService) {
		this.hashGenerator = hashGenerator;
		this.uploadFileService = uploadFileService;
		this.createTransactionService = createTransactionService;
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(final ConsumerRecord<String, RecordModel> record) throws Exception {
		final String storageHash = uploadFileService.uploadFile(record.value().getVideoRecord());
		log.info("Storage hash: {}", storageHash);
		final String contentHash = hashGenerator.generate(record.value().getVideoRecord());

		createTransactionService.createTransaction(record.value().getCameraId(), storageHash, contentHash)
				.forEach(transactionHash -> log.info("Transaction hash: {}", transactionHash));
	}
}
