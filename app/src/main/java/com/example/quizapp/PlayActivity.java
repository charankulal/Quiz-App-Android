package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ImageButton buttonPlay = findViewById(R.id.bt_play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            new AlertDialog.Builder(this).setTitle("Do you want to Exit?").setNegativeButton("No", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setResult(RESULT_OK, new Intent().putExtra("Exit", true));
                    finish();
                }
            }).create().show();
        } else {
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}