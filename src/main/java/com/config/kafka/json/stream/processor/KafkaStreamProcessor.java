package com.config.kafka.json.stream.processor;

import com.config.kafka.json.stream.config.Constants;

import com.config.kafka.json.stream.model.Domain;
import com.config.kafka.json.stream.serdes.DomainSerdes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
 
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;


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


    @Bean
    public KStream<String, Domain> kStream(StreamsBuilder builder) {
           KStream<String, Domain> kstream = builder.stream(Constants.WEB_DOMAIN,
                        Consumed.with(STRING_SERDE, DomainSerdes.serdes()))
                        .mapValues((domain)->{
                              return domain;
                         }).selectKey((key,value)-> value.getDomain()+(value.isDead() ? "(inactive domain)":"(active domain)"))
                        .peek((key,domain)->log.info("Received Domain with key=" +key+" value="+ domain));

           KStream<String, Domain> active_domains= kstream.filter((key,domain)->!domain.isDead());
           KStream<String, Domain> inactive_domains= kstream.filter((key,domain)->domain.isDead());
           active_domains.to(Constants.ACTIVE_WEB_DOMAIN, Produced.with(STRING_SERDE,DomainSerdes.serdes()));
           inactive_domains.to(Constants.INACTIVE_WEB_DOMAIN, Produced.with(STRING_SERDE,DomainSerdes.serdes()));
           return kstream;
    }
}
