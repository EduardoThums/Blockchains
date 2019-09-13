package bigchaindb.ipfs.example.service.kafka;

import bigchaindb.ipfs.example.service.bigchaindb.BigchainDbService;
import bigchaindb.ipfs.example.service.ipfs.IPFSService;
import bigchaindb.ipfs.example.util.HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    private static final String VIDEO_PATH = "/home/eduardo/Downloads/video.mp4";

    private IPFSService ipfsService;

    private BigchainDbService bigchaindbService;

    private HashGenerator hashGenerator;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaServiceImpl(final IPFSService ipfsService, final BigchainDbService bigchaindbService, final HashGenerator hashGenerator, final KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.ipfsService = ipfsService;
        this.bigchaindbService = bigchaindbService;
        this.kafkaTemplate = kafkaTemplate;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public void produceRecord() throws IOException {
        final File file = new File(Objects.requireNonNull(VIDEO_PATH));
        final byte[] record = Files.readAllBytes(file.toPath());

        this.kafkaTemplate.send("producer-topic", record);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.groupId}")
    public void consumeRecord(final ConsumerRecord<String, byte[]> record) throws Exception {
        final String ipfsHash = ipfsService.insert(record.value());
        final String contentHash = hashGenerator.generate(record.value());

        bigchaindbService.createTransaction(ipfsHash, contentHash)
                .forEach(transactionId -> log.info("Transaction hash: {}", transactionId));
    }
}
