package com.meteo.bootstrap;

import com.meteo.adapter.kafka.MeteoTokenKafkaConsumer;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;

@Singleton
public class KafkaConsumerApplication implements ApplicationEventListener<StartupEvent> {
    private final MeteoTokenKafkaConsumer meteoTokenKafkaConsumer;

    KafkaConsumerApplication(final MeteoTokenKafkaConsumer meteoTokenKafkaConsumer) {
        this.meteoTokenKafkaConsumer = meteoTokenKafkaConsumer;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        var comsumerThread = new Thread(meteoTokenKafkaConsumer::consume);
        comsumerThread.setDaemon(true);
        comsumerThread.start();
    }
}
