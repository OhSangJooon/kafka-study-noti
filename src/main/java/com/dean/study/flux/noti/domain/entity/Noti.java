package com.dean.study.flux.noti.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "noti")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Noti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long itemId;
    @Column(columnDefinition = "TEXT")
    private String payload;
    private Instant createdAt;
}
