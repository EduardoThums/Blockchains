package br.com.kafka.example.dzonekafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class SampleProducer extends Thread {
    private static final String TOPIC_NAME = "producer-topic";
    private static final String VIDEO_PATH = "/home/alunoinfo/alunoinfo/video.mp4";

    public static void main(String[] args) throws IOException {

//      ======================================= SERVER CONFIG ================================================
        final Properties props = new Properties();

        //Assign localhost id
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");

        //Criterio para considerar uma request como completa.
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        //Numero de vezes que vai tentar enviar novamente se a request falhar
        props.put(ProducerConfig.RETRIES_CONFIG, 0);

        //Tamanho do buffer que armazena registros ainda nao enviados
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        //Tempo que espera ate enviar uma request (em milisegundos)
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        //Tamanho maximo da request em Bytes, 15MB aqui
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 15728640);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
//      ====================================================================================================

        final Producer<String, byte[]> producer = new KafkaProducer<>(props);

        final File file = new File(Objects.requireNonNull(VIDEO_PATH));

        final byte[] array = Files.readAllBytes(file.toPath());

        final ProducerRecord<String, byte[]> record = new ProducerRecord<>(TOPIC_NAME, array);

        try {
            final RecordMetadata metadata = producer.send(record).get();
            log.info("Record sent with key {} to partition {}", metadata.serializedKeySize(), metadata.partition());

        } catch (ExecutionException | InterruptedException exception) {
            log.info("Error in sending record");
            log.info("{}", exception);
        }

        producer.close();
    }
}