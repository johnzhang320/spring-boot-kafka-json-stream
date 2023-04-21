# spring-boot-kafka-json-stream
crawler company domains into domain pojo objects from stream and saving to web-domain topic and stream consumer retrieves domains from the topic, check isDead() methond in domain object, if domain is still alive, save in active.web-domain topic , if dead, dump to inactive.web-domain topic

Practice the Kafka Json Stream configuration for serializer and deserializer configuration, especially inside stream, we must create customer json servializer / deserializer methods, serializer convert pojo to json string by JsanMapper --> convert json string to byte stream . deserializer make 
stream to json string --> to pojo object. 
we must put the servializer / deserializer into customer surdes into configuration 


