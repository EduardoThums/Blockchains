package fabric.ipfs.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaConfig {

	KAFKA_BROKERS("localhost:19092,localhost:29092,localhost:39092"),

	ACKS_CONFIG("all"),

	RETRIES_CONFIG("0"),

	BATCH_SIZE_CONFIG("16384"),

	LINGER_MS_CONFIG("1"),

	BUFFER_MEMORY_CONFIG("33554432"),

	MAX_REQUEST_SIZE_CONFIG("33554432"),

	TOPIC_NAME("producer-topic"),

	GROUP_ID_CONFIG("consumerGroup10"),

	MAX_NO_MESSAGE_FOUND_COUNT("100"),

	OFFSET_RESET_EARLIER("earliest"),

	MAX_POLL_RECORDS("1"),

	ENABLE_AUTO_COMMIT_CONFIG("false");

	private String value;
}
