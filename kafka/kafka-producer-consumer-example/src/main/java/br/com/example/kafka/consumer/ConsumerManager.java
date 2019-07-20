package br.com.example.kafka.consumer;

import br.com.example.kafka.constant.KafkaConstant;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class ConsumerManager {

	public Consumer<Long, String> createStringConsumer() {
		final Properties properties = getDefaultProperties();
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		return new KafkaConsumer<>(properties);
	}

	public Consumer<Long, byte[]> createByteArrayConsumer() {
		final Properties properties = getDefaultProperties();
		properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, KafkaConstant.MAX_REQUEST_SIZE_CONFIG);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

		return new KafkaConsumer<>(properties);
	}

	private Properties getDefaultProperties() {
		final Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.KAFKA_BROKERS);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstant.GROUP_ID_CONFIG);
		properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, KafkaConstant.MAX_POLL_RECORDS);
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConstant.ENABLE_AUTO_COMMIT_CONFIG);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConstant.OFFSET_RESET_EARLIER);

		return properties;
	}
}
