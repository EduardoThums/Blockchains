package bigchaindb.ipfs.example.service.kafka;

import bigchaindb.ipfs.example.config.KafkaConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaServiceImpl implements KafkaService {

	private static final String VIDEO_PATH = "/home/eduardo/Downloads/video.mp4";

	@Override
	@PostConstruct
	public void startConsumer() {

	}

	@Override
	@PostConstruct
	public void startProducer() throws IOException {
		final Producer<Long, byte[]> producer = createByteArrayProducer();

		final File file = new File(Objects.requireNonNull(VIDEO_PATH));
		final byte[] array = Files.readAllBytes(file.toPath());

		final ProducerRecord<Long, byte[]> record = new ProducerRecord<>(KafkaConfig.TOPIC_NAME.getValue(), array);

		try {
			final RecordMetadata metadata = producer.send(record).get();
			System.out.printf("Record sent to %s on the partition %d\n", metadata.topic(), metadata.partition());

		} catch (ExecutionException | InterruptedException exception) {
			System.out.printf("Exception message: %s", exception.getMessage());
		}

		producer.close();
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

	Producer<Long, byte[]> createByteArrayProducer() {
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
