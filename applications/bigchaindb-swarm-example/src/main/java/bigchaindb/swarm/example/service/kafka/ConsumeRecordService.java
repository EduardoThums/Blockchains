package bigchaindb.swarm.example.service.kafka;

import bigchaindb.swarm.example.service.bigchaindb.CreateTransactionService;
import bigchaindb.swarm.example.service.swarm.UploadFileService;
import bigchaindb.swarm.example.util.HashGenerator;
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
	public void consumeRecord(final ConsumerRecord<String, byte[]> record) throws Exception {
		final String storageHash = uploadFileService.uploadFile(record.value());
		final String contentHash = hashGenerator.generate(record.value());

		createTransactionService.createTransaction(storageHash, contentHash)
				.forEach(transactionHash -> log.info("Transaction hash: {}", transactionHash));
	}
}
