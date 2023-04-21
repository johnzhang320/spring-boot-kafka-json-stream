package com.config.kafka.json.stream.serdes;


import com.config.kafka.json.stream.model.Domain;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

/**
 * Requires the WrapperSerdes to allow this to be added as the default serdes config in the KafkaStreams configuration.
 */
public final class DomainSerdes extends Serdes.WrapperSerde<Domain> {

    public DomainSerdes() {
        // customize com.config.kafka.json.stream.serdes.JsonSerializer and Jcom.config.kafka.json.stream.serdes.JsonDeserializer
        super(new JsonSerializer<>(), new JsonDeserializer<>(Domain.class));
    }
    // usage DomainSerdes.serdes()
    public static Serde<Domain> serdes() {
        JsonSerializer<Domain> serializer = new JsonSerializer<>();
        JsonDeserializer<Domain> deserializer = new JsonDeserializer<>(Domain.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
