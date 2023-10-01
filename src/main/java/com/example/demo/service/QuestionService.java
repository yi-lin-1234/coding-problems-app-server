package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.repository.DifficultyCount;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TopicCount;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    //🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢( POST )🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢🟢
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    //🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵( GET )🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(UUID id) {
        return questionRepository.findById(id);
    }

    public List<Question> searchQuestionsByName(String name) {
        return questionRepository.findByNameContainingIgnoreCase(name);
    }

    public List<TopicCount> countQuestionsByTopic() {
        return questionRepository.countQuestionsByTopic();
    }

    public List<DifficultyCount> countQuestionsByDifficulty() {
        return questionRepository.countQuestionsByDifficulty();
    }
    public long getTotalNumberOfQuestions() {
        return questionRepository.count();
    }


    //🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡( Put )🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡
    public Question updateQuestion(UUID id, Question updatedQuestion) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException("Question with ID " + id + " not found.");
        }
        updatedQuestion.setId(id);
        return questionRepository.save(updatedQuestion);
    }

    //🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴( DELETE )🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴🔴
    public void deleteQuestionById(UUID id) {
        questionRepository.deleteById(id);
    }
}
