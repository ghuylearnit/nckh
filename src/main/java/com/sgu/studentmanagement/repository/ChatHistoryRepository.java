package com.sgu.studentmanagement.repository;

import com.sgu.studentmanagement.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatHistoryRepository - Repository for ChatHistory entity
 */
@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    
    List<ChatHistory> findByUserIdOrderByCreatedAtDesc(Long userId);
}
