package bigchaindb.ipfs.example.config;


import bigchaindb.ipfs.example.model.RecordModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eduardo.thums
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	private String bootstrapServers;

	private String groupId;

	private String autoOffsetReset;

	private String maxPollRecords;

	private String enableAutoCommit;

	private String fetchMaxBytes;

	public KafkaConsumerConfig(
			@Value("${kafka.bootstrapServers}") String bootstrapServers,
			@Value("${kafka.consumer.groupId}") String groupId,
			@Value("${kafka.consumer.autoOffsetReset}") String autoOffsetReset,
			@Value("${kafka.consumer.maxPollRecords}") String maxPollRecords,
			@Value("${kafka.consumer.enableAutoCommit}") String enableAutoCommit,
			@Value("${kafka.consumer.fetchMaxBytes}") String fetchMaxBytes) {
		this.bootstrapServers = bootstrapServers;
		this.groupId = groupId;
		this.autoOffsetReset = autoOffsetReset;
		this.maxPollRecords = maxPollRecords;
		this.enableAutoCommit = enableAutoCommit;
		this.fetchMaxBytes = fetchMaxBytes;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, RecordModel> kafkaListenerContainerFactory() {
		final ConcurrentKafkaListenerContainerFactory<String, RecordModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

	private ConsumerFactory<String, RecordModel> consumerFactory() {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, fetchMaxBytes);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(RecordModel.class));
	}
}
