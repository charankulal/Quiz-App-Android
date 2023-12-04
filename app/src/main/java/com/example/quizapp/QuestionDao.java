package com.example.quizapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * from questions_table")
    LiveData<List<Questions>> getAllQuestions();

}
