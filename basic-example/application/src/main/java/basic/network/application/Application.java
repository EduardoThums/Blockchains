package basic.network.application;

import basic.network.application.kafka.consumer.ConsumerRunner;
import basic.network.application.kafka.producer.ProducerRunner;

public class Application {
	public static void main(String[] args) {

		final Thread producerThread = new Thread(new ProducerRunner());
		producerThread.start();

		final Thread consumerThread = new Thread(new ConsumerRunner());
		consumerThread.start();
	}
}
