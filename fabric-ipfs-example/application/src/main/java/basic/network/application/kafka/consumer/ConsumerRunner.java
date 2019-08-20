package basic.network.application.kafka.consumer;

import basic.network.application.config.KafkaConfig;
import basic.network.application.fabric.FabricManager;
import basic.network.application.ipfs.client.IPFSClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;

public class ConsumerRunner implements Runnable {

	private static final String VIDEO_PATH = "/home/eduardo/Downloads/consumed-video-2.mp4";

	private void runConsumer() throws IOException {
		final ConsumerManager consumerManager = new ConsumerManager();
		final IPFSClient ipfsClient = new IPFSClient();
		final FabricManager fabricManager = new FabricManager();

		final Consumer<Long, byte[]> consumer = consumerManager.createByteArrayConsumer();
		consumer.subscribe(Collections.singletonList(KafkaConfig.TOPIC_NAME.getValue()));

		int maximumNumberOfMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, byte[]> consumerRecords = consumer.poll(Duration.ofMillis(1000));
			if (consumerRecords.count() == 0) {
				maximumNumberOfMessageToFetch++;
				if (maximumNumberOfMessageToFetch > Integer.valueOf(KafkaConfig.MAX_NO_MESSAGE_FOUND_COUNT.getValue())) {
					break;
				} else {
					continue;
				}
			}

			for (final ConsumerRecord<Long, byte[]> record : consumerRecords) {
				final FileOutputStream fileOutputStream = new FileOutputStream(new File(VIDEO_PATH));
				fileOutputStream.write(record.value());
				fileOutputStream.close();

				final File file = new File(Objects.requireNonNull(VIDEO_PATH));

//				System.out.printf("File read and write on the follow path: %s\n", file.toPath());

//				System.out.printf("Hash: %s\n", ipfsClient.insert(record.value()));
				final String hash = ipfsClient.insert(record.value()).toString();
				final String timestamp = Instant.now().toString();

				fabricManager.insert(hash, timestamp).forEach(message -> System.out.printf("Message: %s\n", message));
			}

			consumer.commitAsync();
		}

		consumer.close();
	}

	@Override
	public void run() {
		try {
			this.runConsumer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

