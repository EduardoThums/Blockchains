package basic.network.application.kafka.producer;

import basic.network.application.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerManager {

	public Producer<Long, String> createStringProducer() {
		final Properties properties = getDefaultProperties();

		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		return new KafkaProducer<>(properties);
	}

	Producer<Long, byte[]> createByteArrayProducer() {
		final Properties properties = getDefaultProperties();

		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

		return new KafkaProducer<>(properties);
	}

	private Properties getDefaultProperties() {
		final Properties properties = new Properties();

		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS.getValue());
		properties.put(ProducerConfig.ACKS_CONFIG, KafkaConfig.ACKS_CONFIG.getValue());
		properties.put(ProducerConfig.RETRIES_CONFIG, KafkaConfig.RETRIES_CONFIG.getValue());
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, KafkaConfig.BATCH_SIZE_CONFIG.getValue());
		properties.put(ProducerConfig.LINGER_MS_CONFIG, KafkaConfig.LINGER_MS_CONFIG.getValue());
		properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, KafkaConfig.BUFFER_MEMORY_CONFIG.getValue());
		properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, KafkaConfig.MAX_REQUEST_SIZE_CONFIG.getValue());

		return properties;
	}
}
