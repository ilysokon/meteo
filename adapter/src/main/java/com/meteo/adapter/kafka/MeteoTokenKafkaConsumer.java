package com.meteo.adapter.kafka;

import com.meteo.adapter.persistence.cassandra.NetatmoConfigurationService;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Properties;

import static com.meteo.adapter.kafka.MeteoDataDeserializer.deserialize;

@Singleton
public class MeteoTokenKafkaConsumer implements MeteoKafkaConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(MeteoTokenKafkaConsumer.class);

    private final KafkaConsumer<String, Bytes> consumer;
    private final NetatmoConfigurationService netatmoConfigurationService;

    MeteoTokenKafkaConsumer(final NetatmoConfigurationService netatmoConfigurationService, final KafkaConfiguration kafkaConfiguration) {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "Geometeo-Consumer");
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.bootstrapServers());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BytesDeserializer.class);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfiguration.groupId());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 25);

        consumerProps.put("security.protocol", "SASL_PLAINTEXT");
        consumerProps.put("sasl.mechanism", "SCRAM-SHA-512");
        consumerProps.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username='admin' password='supersecret';");

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(kafkaConfiguration.meteoSourceTopicNames());
        this.netatmoConfigurationService = netatmoConfigurationService;
    }

    public void consume() {
        LOG.info("Meteo Token Kafka consumer is started");
        while (true) {
            ConsumerRecords<String, Bytes> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Bytes> record : records) {
                // Process the received message
                Bytes payload = record.value();
                byte[] bytes = payload.get();
                final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, bytes.length);
                // ... process the payload as needed
                final byte instruction = deserialize(byteBuffer);
                switch (instruction) {
                    // Token is updated
                    case 0 -> netatmoConfigurationService.updateConfiguration();
                }
            }
        }
    }
}
