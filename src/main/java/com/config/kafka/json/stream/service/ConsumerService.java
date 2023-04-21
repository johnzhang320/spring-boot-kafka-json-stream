package com.config.kafka.json.stream.service;

import com.config.kafka.json.stream.config.Constants;
import com.config.kafka.json.stream.model.Domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerService {
    @KafkaListener(topics = Constants.ACTIVE_WEB_DOMAIN, groupId=Constants.CONSUMER_GROUP_ID)
    public void processActive(ConsumerRecord<String, Domain> record) {
         log.info("Consumed Active Web Domains with Key: " + record.key() + ", Value: " + record.value());
    }

    @KafkaListener(topics = Constants.INACTIVE_WEB_DOMAIN, groupId=Constants.CONSUMER_GROUP_ID)
    public void processInActive(ConsumerRecord<String, Domain> record) {
        log.info("Consumed Inactive Web Domains with Key: " + record.key() + ", Value: " + record.value());
    }
}
