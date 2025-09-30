package com.dean.study.flux.noti.scs;

import com.dean.study.flux.noti.domain.entity.Noti;
import com.dean.study.flux.noti.domain.repository.NotiRepository;
import com.dean.study.flux.noti.dto.OrderCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Instant;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class NotiConsumer {
    private final NotiRepository notiRepository;
    private final ObjectMapper objectMapper;

    // TODO DLQ, Slack 발송 2가지
    // SCS로 발행된 메시지 수신 (헤더에 contentType 있음 → 기본 디코딩 사용)
    @Bean
    public Consumer<Message<byte[]>> orderScsConsumer() {
        log.info("[SCS] orderScsConsumer bean created");
        return payload -> {
            log.info("[SCS] orderScsConsumer Event Start");
            try {
                OrderCreatedEvent evt = objectMapper.readValue(payload.getPayload(), OrderCreatedEvent.class);
                String json = objectMapper.writeValueAsString(evt);
                try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
                notiRepository.save(Noti.builder()
                        .orderId(evt.orderId())
                        .itemId(evt.itemId())
                        .payload(json)
                        .createdAt(Instant.now())
                        .build());
                log.info("[SCS] saved NOTI: {}", evt);
            } catch (Exception e) {
                log.error("SCS consume error", e);
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Consumer<Message<byte[]>> orderDltLogger() {
        return msg -> {
            String payload = new String(msg.getPayload());
            log.warn("[DLT] payload={}, headers={}", payload, msg.getHeaders());
            // TODO: 여기서 알림/DB저장/보정 재처리 등 원하는 후처리
            log.error("[orderDltLogger - DLT] 실패 알림발송!! {}", msg);
        };
    }

    // KafkaTemplate로 발행된 메시지 수신 (헤더 없음 → native 디코딩 + JsonDeserializer 필요)
    @Bean
    public Consumer<OrderCreatedEvent> orderKafkaConsumer() {
        log.info("[LowLevelNotiConsumer] orderKafkaConsumer bean created");
        return evt -> {
            log.info("[LowLevelNotiConsumer] received OrderCreatedEvent: {}", evt);
            try {
                notiRepository.save(Noti.builder()
                        .orderId(evt.orderId())
                        .itemId(evt.itemId())
                        .payload(objectMapper.writeValueAsString(evt))
                        .createdAt(Instant.now())
                        .build());
                log.info("[LowLevelNotiConsumer] saved NOTI: {}", evt);
            } catch (JsonProcessingException e) {
                log.error("[LowLevelNotiConsumer] consume error", e);
                throw new RuntimeException(e);
            }
        };
    }
}
