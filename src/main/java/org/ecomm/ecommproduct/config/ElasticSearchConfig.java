package org.ecomm.ecommproduct.config;

import java.time.Duration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@Configuration
public class ElasticSearchConfig {
  String serverUrl = "http://159.203.151.39:9200";
  String encodedPassword = "ZWxhc3RpYzpEa0llZFBQU0Ni";

  // Create the low-level client
  //  @Bean
  //  public ElasticsearchClient getEsClient() {
  //    RestClient restClient =
  //        RestClient.builder(HttpHost.create(serverUrl))
  //            .setDefaultHeaders(
  //                new Header[] {new BasicHeader("Authorization", "Basic " + encodedPassword)})
  //            .build();
  //
  //    // Create the transport with a Jackson mapper
  //    ElasticsearchTransport transport =
  //        new RestClientTransport(restClient, new JacksonJsonpMapper());
  //
  //    // And create the API client
  //    return new ElasticsearchClient(transport);
  //  }

//  @Override
//  public ClientConfiguration clientConfiguration() {
//    return ClientConfiguration.builder()
//        .connectedTo(serverUrl)
//        .withConnectTimeout(Duration.ofSeconds(7))
//        .withHeaders(
//            () -> {
//              HttpHeaders headers = new HttpHeaders();
//              headers.add("Authorization", "Basic " + encodedPassword);
//              return headers;
//            })
//        .build();
//  }
}
