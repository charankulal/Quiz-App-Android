package com.example.quizapp;

import static java.lang.Thread.sleep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView imageViewSpashLogo;
    TextView textViewGoQuiz;
    private final static int EXIT_CODE = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewSpashLogo = findViewById(R.id.splash_imgView);
        textViewGoQuiz = findViewById(R.id.txt_splash_logo_text);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        imageViewSpashLogo.setAnimation(animation);
        textViewGoQuiz.setAnimation(animation);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    goToPlayActivity();
                }
            }
        });
        thread.start();
    }

    // It will restrict user to go back to the splash activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==EXIT_CODE)
        {
            if (resultCode==RESULT_OK)
            {
                if (data.getBooleanExtra("EXIT",true)){
                    finish();
                }
            }
        }
    }

    private void goToPlayActivity() {
        startActivityForResult(new Intent(SplashActivity.this, PlayActivity.class), EXIT_CODE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}