package fabric.api.service.kafka;

import fabric.api.model.RecordModel;
import fabric.api.service.fabric.CreateTransactionService;
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
		//TODO: Remove log when run tests with production environment
		createTransactionService.createTransaction(record.value());

		final Long logEndDate = Instant.now().toEpochMilli();

		produceLogRequestModelService.produceLogRequestModel(record.value().getLogStartDate(), logEndDate);
	}
}
