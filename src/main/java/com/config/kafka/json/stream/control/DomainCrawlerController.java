package com.config.kafka.json.stream.control;

import com.config.kafka.json.stream.model.Domain;
import com.config.kafka.json.stream.model.DomainList;
import com.config.kafka.json.stream.service.DomainCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/domain")
public class DomainCrawlerController {
    @Autowired
    private DomainCrawlerService domainCrawlerService;
    @GetMapping("/lookup/{name}")
    public Mono<DomainList> lookup(@PathVariable("name") String name) {
        Mono<DomainList> domains= domainCrawlerService.crawl(name);
        // show the crawler result
        return domains;
    }
}
