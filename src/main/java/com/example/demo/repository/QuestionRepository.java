package com.example.demo.repository;

import com.example.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {


    //🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵( GET )🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵🔵

    List<Question> findByNameContainingIgnoreCase(String name);

    @Query("SELECT q.topic AS topic, COUNT(q.id) AS count FROM Question q GROUP BY q.topic")
    List<TopicCount> countQuestionsByTopic();

    @Query("SELECT q.difficulty AS difficulty, COUNT(q.id) AS count FROM Question q GROUP BY q.difficulty")
    List<DifficultyCount> countQuestionsByDifficulty();
}
