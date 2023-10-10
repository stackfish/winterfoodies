package com.food.winterfoodies2.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    @Bean
    public ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();// 21
        factory.setUseGlobalResources(false);// 22
        return factory;// 23
    }

    @Bean
    public WebClient webClient() {
        Function<HttpClient, HttpClient> mapper = (client) -> {
            return ((HttpClient)((HttpClient)HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)).doOnConnected((connection) -> {// 31 32 33
                connection.addHandlerLast(new ReadTimeoutHandler(10)).addHandlerLast(new WriteTimeoutHandler(10));// 34 35
            })).responseTimeout(Duration.ofSeconds(1L));// 36 37
        };// 29
        ClientHttpConnector connector = new ReactorClientHttpConnector(this.resourceFactory(), mapper);// 41
        return WebClient.builder().clientConnector(connector).build();// 43
    }
}
