package com.thedigibro.thedigibrobeerclient.web.config;

import com.thedigibro.thedigibrobeerclient.web.client.RestTemplateCustomizer;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
    private final Integer maxtotalconnections;
    private final Integer maxperroute;
    private final Integer requesttimeout;
    private final Integer sockettimeout;

    public BlockingRestTemplateCustomizer(@Value("${sfg.maxtotalconnections}") Integer maxtotalconnections,
                                          @Value("${sfg.maxperroute}") Integer maxperroute,
                                          @Value("${sfg.requesttimeout}") Integer requesttimeout,
                                          @Value("${sfg.sockettimeout}") Integer sockettimeout
                                          ) {
        this.maxtotalconnections = maxtotalconnections;
        this.maxperroute=maxperroute;
        this.requesttimeout=requesttimeout;
        this.sockettimeout=sockettimeout;

    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxtotalconnections);
        connectionManager.setDefaultMaxPerRoute(maxperroute);
        RequestConfig requestConfig=RequestConfig
                .custom()
                .setConnectionRequestTimeout(requesttimeout)
                .setSocketTimeout(sockettimeout)
                .build();
        CloseableHttpClient httpClient= HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
