package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion, textViewScore, textViewQuestionCount, textViewCountDownTimer, textViewCorrect, textViewWrong;
    RadioButton rb1, rb2, rb3, rb4;
    RadioGroup rbGroup;
    Button btNext;
    boolean answered = false;

    List<Questions> quesList;
    Questions currentQ;

    private int questionCounter = 0, questionTotalCount;


    private QuestionViewModel questionViewModel;

    private ColorStateList textColorOfButtons;
    private Handler handler = new Handler();
    private int correctAns = 0, wrongAns = 0,score=0;

    private FinalScoreDialog finalScoreDialog;
    private int totalSizeOfQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupUI();

        textColorOfButtons = rb1.getTextColors();
        finalScoreDialog=new FinalScoreDialog(this);

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                Toast.makeText(QuizActivity.this, "Get it :)", Toast.LENGTH_SHORT).show();
                fetchContent(questions);
            }
        });
    }

    void setupUI() {
        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewCountDownTimer = findViewById(R.id.txtTimer);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewScore = findViewById(R.id.textScore);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        txtQuestion = findViewById(R.id.txtQuestionContainer);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        btNext = findViewById(R.id.button_next);
        textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
        textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

    }

    private void fetchContent(List<Questions> questions) {
        quesList = questions;

        startQuiz();
    }

    private void startQuiz() {
        setQuestionView();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button1) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                } else if (checkedId == R.id.radio_button2) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                } else if (checkedId == R.id.radio_button3) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                } else if (checkedId == R.id.radio_button4) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                }
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        quizOperation();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void quizOperation() {
        answered = true;
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;
        // checkSolutions
        checkSolution(answerNr, rbSelected);

    }

    private void checkSolution(int answerNr, RadioButton rbSelected) {

        switch (currentQ.getAnswer()) {
            case 1:
                if (currentQ.getAnswer() == answerNr) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score+=10;
                    textViewScore.setText("Score: "+String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);


                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);
                }
                break;
            case 2:
                if (currentQ.getAnswer() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score+=10;
                    textViewScore.setText("Score: "+String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);

                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);
                }
                break;
            case 3:
                if (currentQ.getAnswer() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score+=10;
                    textViewScore.setText("Score: "+String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);

                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);
                }
                break;
            case 4:
                if (currentQ.getAnswer() == answerNr) {
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score+=10;
                    textViewScore.setText("Score: "+String.valueOf(score));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);

                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setQuestionView();
                        }
                    }, 500);
                }
                break;

        }
        if (questionCounter == questionTotalCount) {
            btNext.setText("Confirm and Finish");
        }
    }

    private void changetoIncorrectColor(RadioButton rbSelected) {
        rbSelected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
        rbSelected.setTextColor(Color.WHITE);
    }

    private void setQuestionView() {
        rbGroup.clearCheck();
        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);
        questionTotalCount = quesList.size();
//        Collections.shuffle(quesList);
        if (questionCounter < questionTotalCount - 1) {
            currentQ = quesList.get(questionCounter);
            txtQuestion.setText(currentQ.getQuestion());
            rb1.setText(currentQ.getOptA());
            rb2.setText(currentQ.getOptB());
            rb3.setText(currentQ.getOptC());
            rb4.setText(currentQ.getOptD());
            questionCounter++;

            answered = false;

            btNext.setText("Confirm");
            textViewQuestionCount.setText("Questions:" + questionCounter + "/" + (questionTotalCount - 1));
        } else {
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
            totalSizeOfQuiz=quesList.size();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalScoreDialog.finalScoreDialog(correctAns,wrongAns,totalSizeOfQuiz);
                }
            },1000);
        }
    }
}