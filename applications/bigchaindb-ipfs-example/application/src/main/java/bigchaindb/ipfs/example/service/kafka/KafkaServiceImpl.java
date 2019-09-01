package bigchaindb.ipfs.example.service.kafka;

import bigchaindb.ipfs.example.config.KafkaConfig;
import bigchaindb.ipfs.example.service.bigchaindb.BigchaindbService;
import bigchaindb.ipfs.example.service.ipfs.IPFSService;
import bigchaindb.ipfs.example.util.HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    private static final String VIDEO_PATH = "/home/eduardo/Downloads/video.mp4";

    private IPFSService ipfsService;

    private BigchaindbService bigchaindbService;

    private HashGenerator hashGenerator;

    private Producer<Long, byte[]> kafkaProducer;

    private Consumer<Long, byte[]> kafkaConsumer;

    public KafkaServiceImpl(IPFSService ipfsService, BigchaindbService bigchaindbService, HashGenerator hashGenerator) {
        this.ipfsService = ipfsService;
        this.bigchaindbService = bigchaindbService;
        this.hashGenerator = hashGenerator;
        this.kafkaProducer = createByteArrayProducer();
        this.kafkaConsumer = createByteArrayConsumer();
        this.kafkaConsumer.subscribe(Collections.singletonList(KafkaConfig.TOPIC_NAME.getValue()));
    }

    @PostConstruct
    public void initConsumer() {
        final Thread kafkaConsumerThread = new Thread(() -> {
            try {
                startConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        kafkaConsumerThread.start();
    }

    @Override
    public void startConsumer() throws Exception {
        while (true) {
            final ConsumerRecords<Long, byte[]> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));

            for (final ConsumerRecord<Long, byte[]> record : consumerRecords) {
                final String ipfsHash = ipfsService.insert(record.value());
                final String contentHash = hashGenerator.generate(record.value());

                bigchaindbService.createTransaction(ipfsHash, contentHash)
                        .forEach(log::info);
            }

            kafkaConsumer.commitAsync();
        }
    }

    @Override
    public void startProducer() throws IOException {
        final File file = new File(Objects.requireNonNull(VIDEO_PATH));
        final byte[] array = Files.readAllBytes(file.toPath());

        final ProducerRecord<Long, byte[]> record = new ProducerRecord<>(KafkaConfig.TOPIC_NAME.getValue(), array);

        kafkaProducer.send(record);
    }

    private Consumer<Long, byte[]> createByteArrayConsumer() {
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS.getValue());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfig.GROUP_ID_CONFIG.getValue());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaConfig.MAX_POLL_RECORDS.getValue());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConfig.ENABLE_AUTO_COMMIT_CONFIG.getValue());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConfig.OFFSET_RESET_EARLIER.getValue());
        properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, KafkaConfig.MAX_REQUEST_SIZE_CONFIG.getValue());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        return new KafkaConsumer<>(properties);
    }

    private Producer<Long, byte[]> createByteArrayProducer() {
        final Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS.getValue());
        properties.put(ProducerConfig.ACKS_CONFIG, KafkaConfig.ACKS_CONFIG.getValue());
        properties.put(ProducerConfig.RETRIES_CONFIG, KafkaConfig.RETRIES_CONFIG.getValue());
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, KafkaConfig.BATCH_SIZE_CONFIG.getValue());
        properties.put(ProducerConfig.LINGER_MS_CONFIG, KafkaConfig.LINGER_MS_CONFIG.getValue());
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, KafkaConfig.BUFFER_MEMORY_CONFIG.getValue());
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, KafkaConfig.MAX_REQUEST_SIZE_CONFIG.getValue());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        return new KafkaProducer<>(properties);
    }
}
