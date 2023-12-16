package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreDialog {

    private Context mContext;
    private Dialog finalScoreDialog;
    private QuizActivity quizActivityOb;

    private TextView textViewFinalScore;

    public FinalScoreDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void finalScoreDialog(int correctAnswer, int wrongAnswer, int totalSizeOfQuiz) {
        finalScoreDialog = new Dialog(mContext);
        quizActivityOb = new QuizActivity();
        finalScoreDialog.setContentView(R.layout.final_score_dialog);
        final Button btFinalScoreDialog = (Button) finalScoreDialog.findViewById(R.id.bt_final_dialog);
        finalScore(correctAnswer, wrongAnswer, totalSizeOfQuiz);

        btFinalScoreDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalScoreDialog.dismiss();
                Intent intent = new Intent(mContext, QuizActivity.class);
                mContext.startActivity(intent);
            }
        });
        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);
        finalScoreDialog.setCanceledOnTouchOutside(false);

    }

    private void finalScore(int correctAnswer, int wrongAnswer, int totalSizeOfQuiz) {
        int tempScore = 0;
        textViewFinalScore = (TextView) finalScoreDialog.findViewById(R.id.textview_final_score);

        if (correctAnswer == totalSizeOfQuiz) {
            //When user is correct then he will get 20 points or else lose 5 points
            tempScore = (correctAnswer * 20) - (wrongAnswer * 5);
            textViewFinalScore.setText(String.valueOf(tempScore));

        } else if (wrongAnswer == totalSizeOfQuiz) {
            tempScore = 0;
            textViewFinalScore.setText(String.valueOf(tempScore));
        } else if (correctAnswer > wrongAnswer) {
            tempScore = (correctAnswer * 20) - (wrongAnswer * 5);
            textViewFinalScore.setText(String.valueOf(tempScore));
        } else if (wrongAnswer > correctAnswer) {
            tempScore = (wrongAnswer * 5) - (correctAnswer * 20);
            textViewFinalScore.setText(String.valueOf(tempScore));
        } else if (wrongAnswer == correctAnswer) {
            tempScore=(correctAnswer * 20) - (wrongAnswer * 5);
            textViewFinalScore.setText(String.valueOf(tempScore));
        }


    }
}
