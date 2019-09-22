package bigchaindb.ipfs.example.config;

import bigchaindb.ipfs.example.model.RecordModel;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eduardo.thums
 */
@Configuration
public class KafkaProducerConfig {

	@Value("${kafka.bootstrapServers}")
	private String bootstrapServers;

	@Value("${kafka.producer.acks}")
	private String acks;

	@Value("${kafka.producer.retries}")
	private String retries;

	@Value("${kafka.producer.batchSize}")
	private String batchSize;

	@Value("${kafka.producer.lingerMs}")
	private String lingerMs;

	@Value("${kafka.producer.bufferMemory}")
	private String bufferMemory;

	@Value("${kafka.producer.maxRequestSize}")
	private String maxRequestSize;

	public KafkaProducerConfig(@Value("${kafka.bootstrapServers}") String bootstrapServers,
							   @Value("${kafka.producer.acks}") String acks,
							   @Value("${kafka.producer.retries}") String retries,
							   @Value("${kafka.producer.batchSize}") String batchSize,
							   @Value("${kafka.producer.lingerMs}") String lingerMs,
							   @Value("${kafka.producer.bufferMemory}") String bufferMemory,
							   @Value("${kafka.producer.maxRequestSize}") String maxRequestSize) {
		this.bootstrapServers = bootstrapServers;
		this.acks = acks;
		this.retries = retries;
		this.batchSize = batchSize;
		this.lingerMs = lingerMs;
		this.bufferMemory = bufferMemory;
		this.maxRequestSize = maxRequestSize;
	}

	@Bean
	public KafkaTemplate<String, RecordModel> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	private ProducerFactory<String, RecordModel> producerFactory() {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.ACKS_CONFIG, acks);
		properties.put(ProducerConfig.RETRIES_CONFIG, retries);
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
		properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(properties);
	}
}