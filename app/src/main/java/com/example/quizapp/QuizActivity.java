package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion, textViewScore,textViewQuestionCount, textViewCountDownTimer,textViewCorrect,textViewWrong;
    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup rbGroup;
    Button btNext;
    boolean answered=false;

    List<Questions> quesList;
    Questions currentQ;



    private QuestionViewModel questionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupUI();

        questionViewModel= ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                Toast.makeText(QuizActivity.this, "Get it :)", Toast.LENGTH_SHORT).show();
                fetchContent(questions);
            }
        });
    }

    private void fetchContent(List<Questions> questions) {
        quesList= questions;

        //startQuiz() method
    }

    void setupUI(){
        textViewCorrect= findViewById(R.id.txtCorrect);
        textViewCountDownTimer=findViewById(R.id.txtTimer);
        textViewWrong=findViewById(R.id.txtWrong);
        textViewScore=findViewById(R.id.textScore);
        textViewQuestionCount=findViewById(R.id.txtTotalQuestion);
        txtQuestion=findViewById(R.id.txtQuestionContainer);

        rbGroup=findViewById(R.id.radio_group);
        rb1=findViewById(R.id.radio_button1);
        rb2=findViewById(R.id.radio_button2);
        rb3=findViewById(R.id.radio_button3);
        rb4=findViewById(R.id.radio_button4);

        btNext=findViewById(R.id.button_next);

    }
}