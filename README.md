# spring-boot-kafka-json-stream
## Overview 
  Key points:
  Demo:
  1. how to configure the spring-boot kafka json stream
  2. how to create JSON Stream Serde with custom JsonDeserializer
  3. how to consume the stream from source topic and obtain POJO , then check member method in POJO
  4. how to produce new streams to different topics based on checking POJO
  5. make structured model data (one to many) and consumer listens original pojo in consumer side from other topics

## Work Flow

crawler company domains into domain pojo objects from stream and saving to web-domain topic and stream consumer retrieves domains from the topic, check isDead() methond in domain object, if domain is still alive, save in active.web-domain topic , if dead, dump to inactive.web-domain topic

Practice the Kafka Json Stream configuration for serializer and deserializer configuration, especially inside stream, we must create customer json servializer / deserializer methods, serializer convert pojo to json string by JsanMapper --> convert json string to byte stream . deserializer make 
stream to json string --> to pojo object. 
we must put the servializer / deserializer into customer surdes into configuration 


