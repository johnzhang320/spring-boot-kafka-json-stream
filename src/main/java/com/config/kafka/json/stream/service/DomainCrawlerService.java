package com.config.kafka.json.stream.service;

import com.config.kafka.json.stream.config.Constants;
import com.config.kafka.json.stream.mapper.JsonMapper;
import com.config.kafka.json.stream.model.Domain;
import com.config.kafka.json.stream.model.DomainList;
import com.config.kafka.json.stream.model.Subdomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public class DomainCrawlerService {

    @Autowired
    private KafkaTemplate<String, Domain> kafkaTemplate;
    static List<Domain> list = new ArrayList<>();
    public Mono<DomainList> crawl(String name) {

        Mono<DomainList>  domainListMono =
           WebClient.create()
                .get()
                .uri("https://api.domainsdb.info/v1/domains/search?domain="+name+"&zone=com")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DomainList.class);


          int i=0;
        /**
         *  mock dead field randomly to verify if processor can verify alive or inactive domain
         */
        domainListMono.subscribe(domainList->{
            domainList.getDomains().forEach(domain->{
                List<Subdomain> subdomains = new ArrayList<>();
                //test structure json object consumer
                Subdomain subdomain1 = Subdomain.builder()
                        .domain("sub-hobby.com")
                        .category("entertainment")
                        .build();
                subdomains.add(subdomain1);
                Subdomain subdomain2 = Subdomain.builder()
                        .domain("sub-music.com")
                        .category("entertainment")
                        .build();
                subdomains.add(subdomain2);
                Subdomain subdomain3 = Subdomain.builder()
                        .domain("sub-football.com")
                        .category("sports")
                        .build();
                subdomains.add(subdomain3);
                domain.setSub_domain_list(subdomains);
                boolean alive=Math.abs((new Random()).nextInt() % 2) ==1 ? true: false;
                domain.setDead(alive);
                kafkaTemplate.send(Constants.WEB_DOMAIN, domain);
                log.info("Sending Domain is :"+domain);
            });
        });

        return domainListMono;
    }
}
