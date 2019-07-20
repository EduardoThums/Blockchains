package br.com.example.kafka;

import br.com.example.kafka.constant.IPFSConstant;
import br.com.example.kafka.constant.KafkaConstant;
import br.com.example.kafka.consumer.ConsumerManager;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;

public class ConsumerApplication {

	private static final String VIDEO_PATH = "/home/eduardo/Downloads/consumed-video-2.mp4";
	private static final String TXT_FILE_PATH = "/home/eduardo/Downloads/test.txt";

	public static void main(String[] args) throws IOException {
//		runStringConsumer();
		runByteArrayConsumer();
	}

	private static void runStringConsumer() {
		int noMessageToFetch = 0;

		final ConsumerManager consumerManager = new ConsumerManager();

		final Consumer<Long, String> consumer = consumerManager.createStringConsumer();
		consumer.subscribe(Collections.singletonList(KafkaConstant.TOPIC_NAME));

		while (true) {
			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > KafkaConstant.MAX_NO_MESSAGE_FOUND_COUNT) {
					break;
				} else {
					continue;
				}
			}
			consumerRecords.forEach(record -> System.out.printf("Record value %s\n", record.value()));

			consumer.commitAsync();
		}

		consumer.close();
	}

	private static void runByteArrayConsumer() throws IOException {
		int maximumNumberOfMessageToFetch = 0;

		final ConsumerManager consumerManager = new ConsumerManager();
		final IPFS ipfs = new IPFS(new MultiAddress(IPFSConstant.IPFS_DAEMON_ADDRESS));

		final Consumer<Long, byte[]> consumer = consumerManager.createByteArrayConsumer();
		consumer.subscribe(Collections.singletonList(KafkaConstant.TOPIC_NAME));

		while (true) {
			final ConsumerRecords<Long, byte[]> consumerRecords = consumer.poll(Duration.ofMillis(1000));
			if (consumerRecords.count() == 0) {
				maximumNumberOfMessageToFetch++;
				if (maximumNumberOfMessageToFetch > KafkaConstant.MAX_NO_MESSAGE_FOUND_COUNT) {
					break;
				} else {
					continue;
				}
			}

			for (final ConsumerRecord<Long, byte[]> record : consumerRecords) {
//				final FileOutputStream fileOutputStream = new FileOutputStream(new File(VIDEO_PATH));
//				fileOutputStream.write(record.value());
//				fileOutputStream.close();

//				final File file = new File(Objects.requireNonNull(VIDEO_PATH));
				final File file = new File(Objects.requireNonNull(TXT_FILE_PATH));

				final NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
				MerkleNode addResult = ipfs.add(fileWrapper).get(0);

				System.out.printf("%s\n", addResult.hash);
			}

			consumer.commitAsync();
		}

		consumer.close();
	}
}
