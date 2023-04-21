package com.config.kafka.json.stream.model;

import lombok.*;

import java.util.List;

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
