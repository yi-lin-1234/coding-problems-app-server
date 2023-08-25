package com.example.demo.repository;

import com.example.demo.model.Difficulty;

public interface DifficultyCount {
    Difficulty getDifficulty();
    long getCount();
}
