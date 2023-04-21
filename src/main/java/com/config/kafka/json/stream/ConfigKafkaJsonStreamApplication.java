package com.config.kafka.json.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class ConfigKafkaJsonStreamApplication {
	/**
	 *  http://localhost:8099/domain/lookup/google
	 *  http://localhost:8099/domain/lookup/facebook
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConfigKafkaJsonStreamApplication.class, args);
	}
	/**
	 @Bean
	 public KafkaStreams kafkaStreams() {
		 Properties props = new Properties();
		 props.put(StreamsConfig.APPLICATION_ID_CONFIG, "json-streams-app");
		 props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		 StreamsBuilder builder = new StreamsBuilder();
		 ObjectMapper mapper = new ObjectMapper();

		 KStream<String, String> source = builder.stream("input-topic");

		 KStream<String, Domain> domainStream = source.mapValues(value -> {
		 try {
				 JsonNode jsonNode = mapper.readTree(value);
				 ObjectNode objectNode = (ObjectNode) jsonNode;
				 return mapper.treeToValue(objectNode, Domain.class);
			 } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		 }
		 });

		 KStream<String, Domain> filteredStream = domainStream.filter((key, domain) -> domain.getCountry() != null);

		 filteredStream.to("output-topic", Produced.with(Serdes.String(), new JsonSerde<>(Domain.class).serializer()));

		 return new KafkaStreams(builder.build(), props);
	 }

	 The @EnableKafkaStreams annotation enables Kafka Streams in our Spring Boot application.
	 In the kafkaStreams() method, we create a Properties object that defines the Kafka Streams application ID and bootstrap servers.
	 We create a StreamsBuilder object, which will be used to build our Kafka Streams topology.
	 We create an ObjectMapper object, which we'll use to deserialize JSON messages into our Domain objects.
	 We create a KStream<String, String> object, which represents our input stream of JSON messages. The String key represents the Kafka message key, and the String value represents the JSON message itself.
	 We use the mapValues() method to deserialize each JSON message into a Domain object. This method applies a function to each value in the stream and returns a new KStream object where the key is the same as the original key and the value is the result of the function.
	 We use the filter() method to remove any Domain objects whose country field is null.
	 We use the to() method to send the filtered Domain objects to a new Kafka topic called "output-topic".
	 Finally, we create a new KafkaStreams object and return it. This object represents our Kafka Streams application and will start processing messages when we call its start() method.

	 The code you provided will only work if the JSON input can be mapped to a single instance of the Domain class. If the input JSON contains an array of Domain objects, then
	 you would need to modify the code to handle the array and map it to a list of Domain objects.

	 */

	/**
	    below code:
	 In this modified code, if the input JSON is an array, then it will loop through each element of the array and map it to a Domain object,
	 and add it to a list of Domain objects. If the input JSON is not an array, then it will just map the single Domain object to the list.

		 public class DomainJsonDeserializer implements Deserializer<List<Domain>> {

			private final ObjectMapper mapper = new ObjectMapper();

			@Override
			public List<Domain> deserialize(String topic, byte[] value) {
				try {
					JsonNode jsonNode = mapper.readTree(value);
					List<Domain> domains = new ArrayList<>();
					if (jsonNode.isArray()) {
						for (JsonNode node : jsonNode) {
							ObjectNode objectNode = (ObjectNode) node;
							domains.add(mapper.treeToValue(objectNode, Domain.class));
						}
					} else {
						ObjectNode objectNode = (ObjectNode) jsonNode;
						domains.add(mapper.treeToValue(objectNode, Domain.class));
					}
					return domains;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}


	 */
}
