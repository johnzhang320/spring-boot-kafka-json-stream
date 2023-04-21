package com.config.kafka.json.stream.control;

import com.config.kafka.json.stream.service.DomainCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/domain")
public class DomainCrawlerController {
    @Autowired
    private DomainCrawlerService domainCrawlerService;
    @GetMapping("/lookup/{name}")
    public String lookup(@PathVariable("name") String name) {
        domainCrawlerService.crawl(name);
        return "Domain Crawler successfully";
    }
}
