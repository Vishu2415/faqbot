package com.Vishnu.FAQbot.controller;

import com.Vishnu.FAQbot.model.Faq;
import com.Vishnu.FAQbot.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
@CrossOrigin(origins = "*") // Allow requests from frontend (React/HTML)
public class FaqController {

    @Autowired
    private FaqService faqService;

    // Get all FAQs
    @GetMapping
    public List<Faq> getAllFaqs() {
        return faqService.getAllFaqs();
    }

    // Add new FAQ (Admin use)
    @PostMapping
    public Faq addFaq(@RequestBody Faq faq) {
        return faqService.addFaq(faq);
    }

    // Ask a question (User input)
    @PostMapping("/ask")
    public String askQuestion(@RequestBody String userQuestion) {
        return faqService.findBestAnswer(userQuestion);
    }

    @PostMapping("/ask/ai")
    public String askWithAI(@RequestBody String userQuestion) {
        return faqService.findBestAnswerByAi(userQuestion);
    }

    @GetMapping("/search")
    public List<Faq> searchFaqs(@RequestParam String keyword) {
        return faqService.searchFaqs(keyword);
    }
}

