package com.radcom.javaGuildRadcom;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Testcontainers
@SpringBootTest
public class KafkaTestContainerDemoTest {

    private static final String TOPIC = "packetsAndStats";

    protected static Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(10800), new ExposedPort(10800)));

    @Container
    protected static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @BeforeAll
    static void beforeAll() throws ExecutionException, InterruptedException {

        System.out.println("bootstrapServers = " + kafka.getBootstrapServers());
        System.out.println("kafkaPort = " + kafka.getFirstMappedPort());
        System.out.println("exposed ports = " + kafka.getExposedPorts());
        System.out.println();
        //Create Admin and new Topic
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(TOPIC, partitions, replicationFactor);
            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
            KafkaFuture<Void> future = result.values().get(TOPIC);
            future.get();
        }

//        //Create producer
//        Properties producerProperties = new Properties();
//        producerProperties.put("bootstrap.servers", kafka.getBootstrapServers());
//        producerProperties.put("retries", 0);
//        producerProperties.put("batch.size", 16384);
//        producerProperties.put("linger.ms", 1);
//        producerProperties.put("buffer.memory", 33554432);
//        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        Producer<String, String> producer = new KafkaProducer<String, String>(producerProperties);
//        System.out.println();
//
//        //Create consumer
//        Properties consumerProperties = new Properties();
//        consumerProperties.put("bootstrap.servers", kafka.getBootstrapServers());
//        consumerProperties.put("group.id", "packet-retrieval");
//        consumerProperties.put("enable.auto.commit", "true");
//        consumerProperties.put("auto.commit.interval.ms", "1000");
//        consumerProperties.put("session.timeout.ms", "30000");
//        consumerProperties.put("auto.offset.reset", "earliest");
//        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProperties);
//        consumer.subscribe(Arrays.asList(TOPIC));

    }

    @Test
    void tetMethod() throws ExecutionException, InterruptedException {
        //Create producer
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", kafka.getBootstrapServers());
        producerProperties.put("retries", 0);
        producerProperties.put("batch.size", 16384);
        producerProperties.put("linger.ms", 1);
        producerProperties.put("buffer.memory", 33554432);
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(producerProperties);
        System.out.println();

        //Create consumer
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", kafka.getBootstrapServers());
        consumerProperties.put("group.id", "packet-retrieval");
        consumerProperties.put("enable.auto.commit", "true");
        consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("session.timeout.ms", "30000");
        consumerProperties.put("auto.offset.reset", "earliest");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProperties);
        consumer.subscribe(Arrays.asList(TOPIC));

        producer.send(new ProducerRecord<>(TOPIC, "kafka Key", "Kafka value"));
        boolean shouldRun = true;
        while (shouldRun) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {

                System.out.printf("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
                shouldRun = false;
            }
        }
    }

}
