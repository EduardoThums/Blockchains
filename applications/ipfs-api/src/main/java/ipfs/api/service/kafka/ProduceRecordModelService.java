package ipfs.api.service.kafka;

import ipfs.api.model.RecordModel;
import ipfs.api.service.ipfs.UploadFileService;
import ipfs.api.util.HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Slf4j
@Service
public class ProduceRecordModelService {

	private String kafkaTopic;

	private KafkaTemplate<String, RecordModel> kafkaTemplate;

	private UploadFileService uploadFileService;

	private HashGenerator hashGenerator;

	public ProduceRecordModelService(@Value("${kafka.topic.blockchain}") String kafkaTopic,
	                                 KafkaTemplate<String, RecordModel> kafkaTemplate,
	                                 UploadFileService uploadFileService,
	                                 HashGenerator hashGenerator) {
		this.kafkaTopic = kafkaTopic;
		this.kafkaTemplate = kafkaTemplate;
		this.uploadFileService = uploadFileService;
		this.hashGenerator = hashGenerator;
	}

	public void produceRecord(Long cameraId, Long startDate, Long endDate, Long logStartDate, byte[] file) throws Exception {
		final String storageHash = uploadFileService.uploadFile(file);
		final String contentHash = hashGenerator.generateHash(file);

		kafkaTemplate.send(kafkaTopic, new RecordModel(
				cameraId,
				startDate,
				endDate,
				logStartDate,
				contentHash,
				storageHash)
		);
	}
}
