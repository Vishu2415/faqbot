package com.Vishnu.FAQbot.repository;

import com.Vishnu.FAQbot.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByQuestionContainingIgnoreCase(String keyword);
}

