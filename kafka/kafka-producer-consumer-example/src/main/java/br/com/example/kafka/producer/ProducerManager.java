package br.com.example.kafka.producer;

import br.com.example.kafka.constant.KafkaConstant;
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

	public Producer<Long, byte[]> createByteArrayProducer() {
		final Properties properties = getDefaultProperties();

		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

		return new KafkaProducer<>(properties);
	}

	private Properties getDefaultProperties() {
		final Properties properties = new Properties();

		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.KAFKA_BROKERS);
		properties.put(ProducerConfig.ACKS_CONFIG, KafkaConstant.ACKS_CONFIG);
		properties.put(ProducerConfig.RETRIES_CONFIG, KafkaConstant.RETRIES_CONFIG);
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, KafkaConstant.BATCH_SIZE_CONFIG);
		properties.put(ProducerConfig.LINGER_MS_CONFIG, KafkaConstant.LINGER_MS_CONFIG);
		properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, KafkaConstant.BUFFER_MEMORY_CONFIG);
		properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, KafkaConstant.MAX_REQUEST_SIZE_CONFIG);

		return properties;
	}
}
