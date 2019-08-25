package fabric.ipfs.example.service.kafka;

import java.io.IOException;

public interface KafkaService {

	void startConsumer() throws Exception;

	void startProducer() throws IOException;
}
