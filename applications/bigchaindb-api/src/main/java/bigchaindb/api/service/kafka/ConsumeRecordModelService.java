package bigchaindb.api.service.kafka;

import bigchaindb.api.model.RecordModel;
import bigchaindb.api.service.bigchaindb.CreateTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class ConsumeRecordModelService {

	private CreateTransactionService createTransactionService;

	private ProduceLogRequestModelService produceLogRequestModelService;

	public ConsumeRecordModelService(CreateTransactionService createTransactionService, ProduceLogRequestModelService produceLogRequestModelService) {
		this.createTransactionService = createTransactionService;
		this.produceLogRequestModelService = produceLogRequestModelService;
	}

	@KafkaListener(topics = "${kafka.topic.distributedStorage}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, RecordModel> record) throws Exception {
		createTransactionService.createTransaction(record.value())
				.forEach(transactionId -> log.info("Transaction ID: {}", transactionId));

		final Long logEndDate = Instant.now().toEpochMilli();

		produceLogRequestModelService.produceLogRequestModel(record.value().getLogStartDate(), logEndDate);
	}
}
