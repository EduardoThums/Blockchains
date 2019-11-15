package fabric.api.service.kafka;

import fabric.api.model.RecordModel;
import fabric.api.service.fabric.CreateTransactionService;
import fabric.api.service.logger.CreateLogRequestService;
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

	private CreateLogRequestService createLogRequestService;

	public ConsumeRecordModelService(CreateTransactionService createTransactionService, CreateLogRequestService createLogRequestService) {
		this.createTransactionService = createTransactionService;
		this.createLogRequestService = createLogRequestService;
	}

	@KafkaListener(topics = "${kafka.topic.distributedStorage}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, RecordModel> record) throws Exception {
		createTransactionService.createTransaction(record.value());

		final Long logEndDate = Instant.now().toEpochMilli();

		createLogRequestService.createLog(record.value().getLogStartDate(), logEndDate);
	}
}
