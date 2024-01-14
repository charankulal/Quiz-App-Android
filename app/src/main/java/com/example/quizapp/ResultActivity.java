package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore, txtTotalQuizQuestion,txtCorrectQuestion, txtWrongQuestion;
    Button btStartQuizAgain,btMainMenu;

    int highScore=0;
    private static final String SHARED_PREFERENCES="shared_preference";
    private static final String SHARED_PREFERENCES_HIGH_SCORE="shared_preference_high_score";
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txtHighScore=findViewById(R.id.result_tv_HighScore);
        txtTotalQuizQuestion=findViewById(R.id.result_tv_Num_of_Ques);
        txtCorrectQuestion=findViewById(R.id.result_tv_correct_Ques);
        txtWrongQuestion=findViewById(R.id.result_tv_wrong_Ques);

        btMainMenu=findViewById(R.id.bt_result_main_menu);
        btStartQuizAgain=findViewById(R.id.bt_result_play_again);

        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ResultActivity.this,PlayActivity.class);
                startActivity(intent);
            }
        });

        btStartQuizAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ResultActivity.this,QuizActivity.class);
                startActivity(intent);
            }
        });

        loadHighScore();
        Intent intent=getIntent();
        int score=intent.getIntExtra("UserScore",0);
        int totalQuestion=intent.getIntExtra("TotalQuizQuestion",0);
        int correctQuestions=intent.getIntExtra("CorrectQuestions",0);
        int wrongQuestions=intent.getIntExtra("WrongQuestions",0);

        txtTotalQuizQuestion.setText("Total Question: "+String.valueOf(totalQuestion));
        txtCorrectQuestion.setText("Correct Questions: "+String.valueOf(correctQuestions));
        txtWrongQuestion.setText("Wrong Questions: "+String.valueOf(wrongQuestions));

        if(score>highScore)
        {
            updateScore(score);

        }

    }

    private void updateScore(int score) {
        highScore=score;
        txtHighScore.setText("High Score: "+String.valueOf(highScore));

        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCES_HIGH_SCORE,highScore);
        editor.apply();
    }

    private void loadHighScore() {

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        highScore=sharedPreferences.getInt(SHARED_PREFERENCES_HIGH_SCORE,0);
        txtHighScore.setText("High Score: "+String.valueOf(highScore));
    }
    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}