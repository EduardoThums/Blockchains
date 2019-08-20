package bigchaindb.ipfs.example.service.kafka;

import java.io.IOException;

public interface KafkaService {

	void startConsumer();

	void startProducer() throws IOException;
}
