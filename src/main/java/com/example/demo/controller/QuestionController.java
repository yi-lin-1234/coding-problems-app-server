package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.repository.DifficultyCount;
import com.example.demo.repository.TopicCount;
import com.example.demo.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "https://unrivaled-paprenjak-c9ce77.netlify.app/")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    //🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵( GET )🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        logger.info("Fetching all questions...");
        List<Question> questions = questionService.getAllQuestions();
        logger.info("Fetched {} questions.", questions.size());
        return ResponseEntity.ok(questions);
    }

    @GetMapping("question/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable UUID id) {
        logger.info("Fetching question with ID: {}", id);
        Optional<Question> questionOptional = questionService.getQuestionById(id);
        if (questionOptional.isPresent()) {
            return ResponseEntity.ok(questionOptional.get());
        } else {
            logger.warn("Question with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> searchQuestionsByName(@RequestParam String name) {
        logger.info("Searching questions by name: {}", name);
        List<Question> questions = questionService.searchQuestionsByName(name);
        if (questions.isEmpty()) {
            logger.info("No questions found with name: {}", name);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/count-by-topic")
    public ResponseEntity<List<TopicCount>> countQuestionsByTopic() {
        logger.info("Counting questions by topic...");
        List<TopicCount> topicCounts = questionService.countQuestionsByTopic();
        logger.info("Counted questions by topic.");
        return ResponseEntity.ok(topicCounts);
    }

    @GetMapping("/count-by-difficulty")
    public ResponseEntity<List<DifficultyCount>> countQuestionsByDifficulty() {
        logger.info("Counting questions by difficulty...");
        List<DifficultyCount> difficultyCounts = questionService.countQuestionsByDifficulty();
        logger.info("Counted questions by difficulty.");
        return ResponseEntity.ok(difficultyCounts);
    }


    //🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢( POST )🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢
    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        logger.info("Creating new question...");
        Question createdQuestion = questionService.createQuestion(question);
        logger.info("Created question with ID: {}", createdQuestion.getId());
        URI location = URI.create("/question/" + createdQuestion.getId());
        return ResponseEntity.created(location).body(createdQuestion);
    }


    //🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡( Put )🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡

    @PutMapping("/question/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable UUID id, @RequestBody Question updatedQuestion) {
        logger.info("Updating question with ID: {}", id);
        try {
            Question question = questionService.updateQuestion(id, updatedQuestion);
            logger.info("Updated question with ID: {}", id);
            return ResponseEntity.ok(question);
        } catch (EntityNotFoundException e) {
            logger.error("Error updating question with ID {}. Reason: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    //🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴( DELETE )🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴
    @DeleteMapping("/question/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        logger.info("Deleting question with ID: {}", id);
        questionService.deleteQuestionById(id);
        logger.info("Deleted question with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

}
