package com.meteo.adapter.kafka;

class AppConfigs {
    final static String bootstrapServers = "kafka-0.kafka-headless.kafka.svc.cluster.local:9092,kafka-1.kafka-headless.kafka.svc.cluster.local:9092,kafka-2.kafka-headless.kafka.svc.cluster.local:9092";
    final static int numEvents = 1000000;
    public final static String groupID = "meteo";
    public final static String[] meteoSourceTopicNames = {"meteo.netatmo.token"};
}
