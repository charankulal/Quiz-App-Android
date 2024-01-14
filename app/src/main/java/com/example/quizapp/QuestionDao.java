package com.example.quizapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT  * from questions_table order by RANDOM() LIMIT 6")
    LiveData<List<Questions>> getAllQuestions();

    @Insert
    void insert(Questions questions);

}
