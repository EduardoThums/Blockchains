package bigchaindb.ipfs.example.service.kafka;

import bigchaindb.ipfs.example.model.RecordModel;
import bigchaindb.ipfs.example.service.bigchaindb.CreateTransactionService;
import bigchaindb.ipfs.example.service.ipfs.UploadFileService;
import bigchaindb.ipfs.example.util.HashGenerator;
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

	private CreateTransactionService createTransactionService;

	private UploadFileService uploadFileService;

	private HashGenerator hashGenerator;

	public ConsumeRecordService(CreateTransactionService createTransactionService, UploadFileService uploadFileService, HashGenerator hashGenerator) {
		this.createTransactionService = createTransactionService;
		this.uploadFileService = uploadFileService;
		this.hashGenerator = hashGenerator;
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, RecordModel> record) throws Exception {
		final String storageHash = uploadFileService.uploadFile(record.value().getVideoRecord());
		final String contentHash = hashGenerator.generate(record.value().getVideoRecord());

		createTransactionService.createTransaction(record.value().getCameraId(), storageHash, contentHash)
				.forEach(transactionHash -> log.info("Transaction hash: {}", transactionHash));
	}
}
