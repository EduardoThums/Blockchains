package bigchaindb.ipfs.example.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public interface KafkaService {

    void produceRecord() throws IOException;

    void consumeRecord(ConsumerRecord<String, byte[]> consumerRecord) throws Exception;
}
