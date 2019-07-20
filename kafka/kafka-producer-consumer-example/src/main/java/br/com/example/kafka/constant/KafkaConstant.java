package br.com.example.kafka.constant;


public interface KafkaConstant {

	String KAFKA_BROKERS = "localhost:19092,localhost:29092,localhost:39092";

	String ACKS_CONFIG = "all";

	int RETRIES_CONFIG = 0;

	int BATCH_SIZE_CONFIG = 16384;

	int LINGER_MS_CONFIG = 1;

	int BUFFER_MEMORY_CONFIG = 33554432;

	int MAX_REQUEST_SIZE_CONFIG = 15728640;

	String TOPIC_NAME = "producer-topic";

	String GROUP_ID_CONFIG = "consumerGroup10";

	Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

	String OFFSET_RESET_EARLIER = "earliest";

	Integer MAX_POLL_RECORDS = 1;

	String ENABLE_AUTO_COMMIT_CONFIG = "false";
}
