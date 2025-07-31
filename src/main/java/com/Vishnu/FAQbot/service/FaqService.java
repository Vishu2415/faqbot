package com.Vishnu.FAQbot.service;

import com.Vishnu.FAQbot.model.Faq;
import com.Vishnu.FAQbot.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private OpenRouterService openRouterService;

    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    public Faq addFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    public String findBestAnswer(String userQuestion) {
        List<Faq> faqs = faqRepository.findAll();
        for (Faq faq : faqs) {
            if (userQuestion.toLowerCase().contains(faq.getQuestion().toLowerCase())) {
                return faq.getAnswer();
            }
        }
        return "Sorry, I couldn't find an answer. Try rephrasing your question.";
    }

    public Optional<Faq> getFaqById(Long id) {
        return faqRepository.findById(id);
    }

    public String findBestAnswerByAi(String userQuestion) {
        String lower = userQuestion.toLowerCase();
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        if (lower.contains("date") && lower.contains("today")) {
            return "📅 Today's date is: " + today;
        } else if (lower.contains("time") && (lower.contains("now") || lower.contains("current"))) {
            return "🕒 Current time is: " + time;
        } else if (lower.contains("day") && lower.contains("today")) {
            DayOfWeek day = today.getDayOfWeek();
            return "📆 Today is: " + day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        } else if (lower.contains("year")) {
            return "📅 Current year is: " + today.getYear();
        } else if (lower.contains("month")) {
            Month month = today.getMonth();
            return "🗓️ Current month is: " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        }

        return openRouterService.getChatResponse(userQuestion);
    }

    public List<Faq> searchFaqs(String keyword) {
        return faqRepository.findByQuestionContainingIgnoreCase(keyword);
    }
}
