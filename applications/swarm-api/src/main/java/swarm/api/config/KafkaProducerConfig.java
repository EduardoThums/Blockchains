package swarm.api.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import swarm.api.model.RecordModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eduardo.thums
 */
@Configuration
public class KafkaProducerConfig {

	private String bootstrapServers;

	private String acks;

	private String retries;

	private String batchSize;

	private String lingerMs;

	private String bufferMemory;

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
	public KafkaTemplate<String, RecordModel> kafkaStringTemplate() {
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
