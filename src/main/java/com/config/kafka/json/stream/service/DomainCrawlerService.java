package com.config.kafka.json.stream.service;

import com.config.kafka.json.stream.config.Constants;
import com.config.kafka.json.stream.model.Domain;
import com.config.kafka.json.stream.model.DomainList;
import com.config.kafka.json.stream.model.Subdomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class DomainCrawlerService {

    @Autowired
    private KafkaTemplate<String, Domain> kafkaTemplate;



    public void crawl(String name) {
        Mono<DomainList> domainListMono = WebClient.create()
                .get()
                .uri("https://api.domainsdb.info/v1/domains/search?domain="+name+"&zone=com")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DomainList.class);
        int i=0;

        domainListMono.subscribe(domainList->{
            domainList.getDomains().forEach(domain->{
                List<Subdomain> subdomains = new ArrayList<>();
                //test structure json object consumer
                Subdomain subdomain1 = Subdomain.builder()
                        .domain("sub-hobby.com")
                        .active(false)
                        .category("entertainment")
                        .build();
                subdomains.add(subdomain1);
                Subdomain subdomain2 = Subdomain.builder()
                        .domain("sub-music.com")
                        .active(true)
                        .category("entertainment")
                        .build();
                subdomains.add(subdomain2);
                Subdomain subdomain3 = Subdomain.builder()
                        .domain("sub-football.com")
                        .active(true)
                        .category("sports")
                        .build();
                subdomains.add(subdomain3);
                domain.setSub_domain_list(subdomains);
                kafkaTemplate.send(Constants.WEB_DOMAIN, domain);
                log.info("Sending Domain is :"+domain.getDomain());
            });
        });
    }
}
