package basic.network.application.kafka.producer;

import basic.network.application.config.KafkaConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ProducerRunner implements Runnable {

	private static final String VIDEO_PATH = "/home/eduardo/Downloads/video.mp4";

	private void runProducer() throws IOException {
		final ProducerManager producerManager = new ProducerManager();
		final Producer<Long, byte[]> producer = producerManager.createByteArrayProducer();

		final File file = new File(Objects.requireNonNull(VIDEO_PATH));
		final byte[] array = Files.readAllBytes(file.toPath());

		final ProducerRecord<Long, byte[]> record = new ProducerRecord<>(KafkaConfig.TOPIC_NAME.getValue(), array);

		try {
			final RecordMetadata metadata = producer.send(record).get();
			System.out.printf("Record sent to %s on the partition %d\n", metadata.topic(), metadata.partition());

		} catch (ExecutionException | InterruptedException exception) {
			System.out.printf("Exception message: %s", exception.getMessage());
		}

		producer.close();
	}

	@Override
	public void run() {
		try {
			this.runProducer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
