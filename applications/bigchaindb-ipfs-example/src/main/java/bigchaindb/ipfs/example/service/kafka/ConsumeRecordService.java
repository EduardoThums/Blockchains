package bigchaindb.ipfs.example.service.kafka;

import bigchaindb.ipfs.example.model.RecordModel;
import bigchaindb.ipfs.example.model.VideoAssetModel;
import bigchaindb.ipfs.example.service.bigchaindb.CreateTransactionService;
import bigchaindb.ipfs.example.service.ipfs.UploadFileService;
import bigchaindb.ipfs.example.util.HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

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

//		final VideoAssetModel videoAssetModel = VideoAssetModel
//				.builder()
//				.startDate(Instant.now().getEpochSecond())
//				.endDate(Instant.now().plus(Duration.ofHours(10)).getEpochSecond())
//				.storageHash(storageHash)
//				.contentHash(contentHash)
//				.cameraId(record.value().getCameraId())
//				.build();

		final VideoAssetModel videoAssetModel = new VideoAssetModel(
				Instant.now().getEpochSecond(),
				Instant.now().plus(Duration.ofHours(10)).getEpochSecond(),
				storageHash,
				contentHash,
				record.value().getCameraId());

		createTransactionService.createTransaction(videoAssetModel)
				.forEach(transactionHash -> log.info("Transaction hash: {}", transactionHash));
	}
}
