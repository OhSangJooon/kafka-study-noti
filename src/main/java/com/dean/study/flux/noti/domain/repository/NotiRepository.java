package com.dean.study.flux.noti.domain.repository;

import com.dean.study.flux.noti.domain.entity.Noti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepository extends JpaRepository<Noti, Long> {}
