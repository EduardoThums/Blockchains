package swarm.api.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import swarm.api.model.RecordModel;
import swarm.api.service.swarm.UploadFileService;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class ConsumeRecordModelService {

	private UploadFileService uploadFileService;

	private ProduceRecordModelService produceRecordModelService;

	public ConsumeRecordModelService(UploadFileService uploadFileService, ProduceRecordModelService produceRecordModelService) {
		this.uploadFileService = uploadFileService;
		this.produceRecordModelService = produceRecordModelService;
	}

	@KafkaListener(topics = "${kafka.topic.video}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, RecordModel> record) {
		final RecordModel recordModel = record.value();
		final String storageHash = uploadFileService.uploadFile(recordModel.getFile());

		recordModel.setStorageHash(storageHash);

		final RecordModel newRecordModel = new RecordModel(
				recordModel.getCameraId(),
				recordModel.getStartDate(),
				recordModel.getEndDate(),
				recordModel.getLogStartDate(),
				recordModel.getContentHash(),
				storageHash);

		produceRecordModelService.produceRecord(newRecordModel);
	}
}
