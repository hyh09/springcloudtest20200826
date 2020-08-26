//package com.example.demo.conf;
//
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.Resource;
//import java.time.Duration;
//
///**
// * Created by dell on 2020/8/24.
// */
//@Configuration
//public class RestTemplateConfig {
//
//    @Resource
//    private RestTemplateBuilder restTemplateBuilder;
//
//    @Bean
//    public RestTemplate buildRestTemplate() {
//        return restTemplateBuilder
//                .rootUri("http://10.128.88.2:9090")
//                .setConnectTimeout(Duration.ofSeconds(5))
//                .build();
//    }
//}
