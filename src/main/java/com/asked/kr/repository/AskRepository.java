package com.asked.kr.repository;

import com.asked.kr.domain.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AskRepository extends JpaRepository<Ask,Long> {
    List<Ask> findAskByReceiverEmail(String memberEmail);
}
