//package com.dean.study.flux.noti.kafka;
//
//import com.dean.study.flux.noti.domain.entity.Noti;
//import com.dean.study.flux.noti.domain.repository.NotiRepository;
//import com.dean.study.flux.noti.dto.OrderCreatedEvent;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class LowLevelNotiConsumer {
//    private final NotiRepository notiRepository;
//    private final ObjectMapper objectMapper;
////  TODO SCS 테스트 후 주석 해제
//    @KafkaListener(topics = "order-created-kafka", groupId = "noti-kafka-group")
//    public void consume(OrderCreatedEvent evt) throws Exception {
//        log.info("[LowLevelNotiConsumer] received OrderCreatedEvent: {}", evt);
//        notiRepository.save(Noti.builder()
//                .orderId(evt.orderId())
//                .itemId(evt.itemId())
//                .payload(objectMapper.writeValueAsString(evt))
//                .createdAt(Instant.now())
//                .build());
//        log.info("[KafkaListener] saved NOTI: {}", evt);
//    }
//}
