package com.config.kafka.json.stream.processor;

import com.config.kafka.json.stream.config.Constants;
import com.config.kafka.json.stream.model.Domain;
import com.config.kafka.json.stream.serdes.DomainSerdes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

@EnableKafkaStreams
@EnableKafka
@Configuration
@Slf4j
public class KafkaStreamProcessor {
    @Bean
    public Serde<Domain> DomainSerde() {
        return new JsonSerde<>(Domain.class);
    }
    private static final Serde<String> STRING_SERDE = Serdes.String();
    private static final Serde<Long> LONG_SERDE = Serdes.Long();
    private static Boolean randData() {
        Random rand = new Random();
        return Math.abs(rand.nextInt() % 2) ==1 ? true: false;
    }

    @Bean
    public KStream<String, Domain> kStream(StreamsBuilder builder) {

        KStream<String, Domain> kstream = builder.stream(Constants.WEB_DOMAIN, Consumed.with(STRING_SERDE, DomainSerdes.serdes()))
                .mapValues((domain)->{
                    domain.setDead(randData());
                    return domain;
                }).peek((key,domain)->log.info("Received Domain with key="+key+", domain="+domain));


        KStream<String, Domain> active_domains= kstream.filter((key,domain)->domain.isDead());

        KStream<String, Domain> inactive_domains= kstream.filter((key,domain)->domain.isDead());

        active_domains.to(Constants.ACTIVE_WEB_DOMAIN, Produced.with(STRING_SERDE,DomainSerdes.serdes()));
        inactive_domains.to(Constants.INACTIVE_WEB_DOMAIN, Produced.with(STRING_SERDE,DomainSerdes.serdes()));

        return null;
    }
}
