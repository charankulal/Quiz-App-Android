package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion, textViewScore, textViewQuestionCount, textViewCountDownTimer, textViewCorrect, textViewWrong;
    RadioButton rb1, rb2, rb3, rb4;
    RadioGroup rbGroup;
    Button btNext;
    boolean answered = false;

    List<Questions> quesList;
    Questions currentQ;

    private int questionCounter = 0, questionTotalCount;


    private final Handler handler = new Handler();
    private int correctAns = 0, wrongAns = 0, score = 0;

    private FinalScoreDialog finalScoreDialog;
    private int totalSizeOfQuiz;
    private WrongDialog wrongDialog;
    private CorrectDialog correctDialog;
    private int flag = 0;
    private PlayAudioForAnswers playAudioForAnswers;
    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    public ColorStateList textColorOfButtons;
    private long backPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupUI();

        textColorOfButtons = rb1.getTextColors();
        finalScoreDialog = new FinalScoreDialog(this);
        wrongDialog = new WrongDialog(this);
        correctDialog = new CorrectDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);

        QuestionViewModel questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                Toast.makeText(QuizActivity.this, "Get it :)", Toast.LENGTH_SHORT).show();
                fetchContent(questions);
            }
        });
    }

    @SuppressLint("SetTextI18n")
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
        countDownTimer.cancel();
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
                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);
                    flag = 1;
                    playAudioForAnswers.setAudioForAnswers(flag);


                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    final String correctAnswer = (String) rb1.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    flag = 2;
                    playAudioForAnswers.setAudioForAnswers(flag);

                }
                break;
            case 2:
                if (currentQ.getAnswer() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);
                    flag = 1;
                    playAudioForAnswers.setAudioForAnswers(flag);


                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    final String correctAnswer = (String) rb2.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    flag = 2;
                    playAudioForAnswers.setAudioForAnswers(flag);

                }
                break;
            case 3:
                if (currentQ.getAnswer() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);
                    flag = 1;
                    playAudioForAnswers.setAudioForAnswers(flag);


                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    final String correctAnswer = (String) rb3.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    flag = 2;
                    playAudioForAnswers.setAudioForAnswers(flag);

                }
                break;
            case 4:
                if (currentQ.getAnswer() == answerNr) {
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
                    score += 10;
                    textViewScore.setText("Score: " + String.valueOf(score));
                    correctDialog.correctDialog(score, this);
                    flag = 1;
                    playAudioForAnswers.setAudioForAnswers(flag);


                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    final String correctAnswer = (String) rb4.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                    flag = 2;
                    playAudioForAnswers.setAudioForAnswers(flag);

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

    public void setQuestionView() {
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
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resultData();
                }
            }, 1000);
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDownTimer.setText(timeFormatted);
        if (timeLeftInMillis < 10000) {
            textViewCountDownTimer.setTextColor(Color.RED);
            flag = 3;
            playAudioForAnswers.setAudioForAnswers(flag);
        } else {
            textViewCountDownTimer.setTextColor(textColorOfButtons);
        }

        if (timeLeftInMillis == 0) {
            Toast.makeText(this, "Time is up", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);
                }
            }, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void resultData() {
        Intent resultOfQuiz = new Intent(QuizActivity.this, ResultActivity.class);
        resultOfQuiz.putExtra("UserScore", score);
        resultOfQuiz.putExtra("TotalQuizQuestion", (questionTotalCount - 1));
        resultOfQuiz.putExtra("CorrectQuestions", correctAns);
        resultOfQuiz.putExtra("WrongQuestions", wrongAns);
        startActivity(resultOfQuiz);
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(QuizActivity.this, PlayActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }


}