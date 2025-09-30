//package com.dean.study.flux.noti.config;
//
//import com.dean.study.flux.noti.dto.OrderCreatedEvent;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.util.backoff.FixedBackOff;
//
//@Configuration
//public class KafkaNotiConfig {
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> kafkaListenerContainerFactory(
//            ConsumerFactory<String, OrderCreatedEvent> cf) {
//        var f = new ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent>();
//        f.setConsumerFactory(cf);
//
//        // 역직렬화 예외/그 외 예외 모두에 대한 backoff 설정(예: 1초 간격, 3회)
//        var backoff = new FixedBackOff(1000L, 3);
//        f.setCommonErrorHandler(new DefaultErrorHandler(backoff));
//        return f;
//    }
//}
