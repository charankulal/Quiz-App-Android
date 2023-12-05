package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionViewModel= ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                Toast.makeText(QuizActivity.this, "Get it :)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}