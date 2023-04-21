package com.config.kafka.json.stream.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DomainList {
    List<Domain> domains;
}
