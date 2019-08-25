package fabric.ipfs.example.service.kafka;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface KafkaService {

	void startConsumer() throws Exception;

	void startProducer() throws IOException, ExecutionException, InterruptedException;
}
