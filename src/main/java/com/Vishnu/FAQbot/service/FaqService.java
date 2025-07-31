package com.Vishnu.FAQbot.service;



import com.Vishnu.FAQbot.model.Faq;
import com.Vishnu.FAQbot.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private OpenRouterService openRouterService;


    // Get all FAQs
    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    // Add a new FAQ
    public Faq addFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    // Find answer by simple keyword matching (basic logic for now)
    public String findBestAnswer(String userQuestion) {
        List<Faq> faqs = faqRepository.findAll();
        for (Faq faq : faqs) {
            if (userQuestion.toLowerCase().contains(faq.getQuestion().toLowerCase())) {
                return faq.getAnswer();
            }
        }
        return "Sorry, I couldn't find an answer. Try rephrasing your question.";
    }

    // (Optional) Get FAQ by ID
    public Optional<Faq> getFaqById(Long id) {
        return faqRepository.findById(id);
    }

    public String findBestAnswerByAi(String userQuestion) {
        return openRouterService.getChatResponse(userQuestion);
    }

}
