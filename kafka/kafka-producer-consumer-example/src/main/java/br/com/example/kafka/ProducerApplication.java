package br.com.example.kafka;

import br.com.example.kafka.constant.KafkaConstant;
import br.com.example.kafka.producer.ProducerManager;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ProducerApplication {

	private static final String VIDEO_PATH = "/home/eduardo/Downloads/video.mp4";

	private static final String TXT_FILE_PATH = "/home/eduardo/Downloads/test.txt";

	public static void main(String[] args) throws IOException {
		final ProducerManager producerManager = new ProducerManager();

//		runStringProducer(producerManager);
		runByteArrayProducer(producerManager);
	}

	private static void runStringProducer(ProducerManager producerManager) throws IOException {
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		final Producer<Long, String> producer = producerManager.createStringProducer();

		String message = "";

		while (!message.equals("exit")) {
			message = bufferedReader.readLine();

			final ProducerRecord<Long, String> record = new ProducerRecord<>(KafkaConstant.TOPIC_NAME, message);

			try {
				final RecordMetadata metadata = producer.send(record).get();
				System.out.printf("Record %s sent to %s on the %d partition\n", metadata.serializedValueSize(), metadata.topic(), metadata.partition());

			} catch (ExecutionException | InterruptedException exception) {
				System.out.printf("Error while sending record. Exception: %s\n", exception.getMessage());
			}
		}

		producer.close();
	}

	private static void runByteArrayProducer(ProducerManager producerManager) throws IOException {
		final Producer<Long, byte[]> producer = producerManager.createByteArrayProducer();

//		final File file = new File(Objects.requireNonNull(VIDEO_PATH));
		final File file = new File(Objects.requireNonNull(TXT_FILE_PATH));

		final byte[] array = Files.readAllBytes(file.toPath());

		final ProducerRecord<Long, byte[]> record = new ProducerRecord<>(KafkaConstant.TOPIC_NAME, array);

		try {
			final RecordMetadata metadata = producer.send(record).get();
			System.out.printf("Record sent to %s on the partition %d\n", metadata.topic(), metadata.partition());

		} catch (ExecutionException | InterruptedException exception) {
			System.out.printf("Exception message: %s", exception.getMessage());
		}

		producer.close();
	}
}
