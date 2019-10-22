package ipfs.api.service.kafka;

import ipfs.api.model.RecordModel;
import ipfs.api.service.ipfs.UploadFileService;
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

	private UploadFileService uploadFileService;

	private ProduceRecordModelService produceRecordModelService;

	public ConsumeRecordModelService(UploadFileService uploadFileService, ProduceRecordModelService produceRecordModelService) {
		this.uploadFileService = uploadFileService;
		this.produceRecordModelService = produceRecordModelService;
	}

	@KafkaListener(topics = "${kafka.topic.video}", groupId = "${kafka.consumer.groupId}")
	public void consumeRecord(ConsumerRecord<String, RecordModel> record) throws Exception {
		final RecordModel recordModel = record.value();
		final String storageHash = uploadFileService.uploadFile(recordModel.getFile());

		recordModel.setStorageHash(storageHash);

		produceRecordModelService.produceRecord(recordModel);
	}
}
