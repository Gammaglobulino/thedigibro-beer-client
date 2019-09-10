package com.thedigibro.thedigibrobeerclient.web.client;
import org.springframework.web.client.RestTemplate;

@FunctionalInterface
public interface RestTemplateCustomizer {
    void customize(RestTemplate restTemplate);
}
