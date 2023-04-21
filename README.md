# spring-boot-kafka-json-stream
 
## Key points:
  
  1. how to configure the spring-boot kafka json stream
  2. how to create JSON Stream Serde with custom JsonDeserializer
  3. how to consume the stream from source topic and obtain POJO , then check member method in POJO
  4. how to produce new streams to different topics based on checking POJO
  5. make structured model data (one to many) and consumer listens original pojo in consumer side from other topics

## Work Flow

![](images/workflow.png)
Domain crawler code gathers company domains into domain pojo objects from web and saving to domain topic and stream consumer listens domain POJO,
check isDead() methond in domain pojo, if domain is still alive, save in active domain topic , if dead, dump to inactive domain topic

   
## Start Zookeeper and Kafka
       download kafka_2.12-2.1.0.tgz from https://archive.apache.org/dist/kafka/2.1.0/kafka_2.12-2.1.0.tgz
       tar cvx kafka_2.12-2.1.0.tgz
       add $KAFKA_HOME point to your kafka installation directory 
       cd ./spring-boot-kafka-event-driven/kafka_start_stop
       chmod 755 *
       zookeeper_start.sh
       kafka_start.sh
       jps
       make sure following two processes running
       xxxx QuorumPeerMain
       xxxx Kafka
       
### All topics will be automatically created by java code   
   we can use shell script in directory kafka_start_stop to show topic, producer and consumer status content 

## Running Evirnomenet
     JDK17
     Spring boot 2.7.10
     spring-kafka
     kafka-streams
     spring-boot-starter-webflux
## Data Model
### Domain class
     @Data
     @AllArgsConstructor
     @NoArgsConstructor
     @Builder
     @ToString
     public class Domain {
         private String domain; //     "domain": "facebook-hosting.com",
         private String  create_date;        //   "create_date": "2023-02-13T06:05:59.477155",
         private String  update_date;        //      "update_date": "2023-02-13T06:05:59.477157",
         private String  country;         //     "country": null,
         private boolean   isDead;        //      "isDead": "False",
         private String   A;        //     "A": null,
         private String   NS;         //    "NS": null,
         private String   CNAME;         //     "CNAME": null,
         private String   MX;        //    "MX": null,
         private String   TXT;        //     "TXT": null
         private List<Subdomain> sub_domain_list;  // test structured json
     }
     
 ### SubDomain class 
       @Data
       @AllArgsConstructor
       @NoArgsConstructor
       @Builder
       public class Subdomain {
         private String domain;
         private boolean active;
         private String category;
     }
### Configure Stream Objects producer and consumer are similar to my another repository 
