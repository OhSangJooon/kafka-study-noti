package com.dean.study.flux.noti.dto;

import java.time.Instant;

public record OrderCreatedEvent(Long orderId, Long itemId, String status, Instant ts) {}