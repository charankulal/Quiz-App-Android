package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WrongDialog {

    private Context mContext;
    private Dialog wrongAnswerDialog;
    private QuizActivity quizActivityOb;
    private QuizActivity mquizActivity;

    public WrongDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void wrongDialog(String correctAnswer, QuizActivity quizActivity) {
        mquizActivity=quizActivity;
        wrongAnswerDialog = new Dialog(mContext);
        wrongAnswerDialog.setContentView(R.layout.wrong_dialog);
        final Button btwrongAnswerDialog = (Button) wrongAnswerDialog.findViewById(R.id.bt_wrong_dialog);
        TextView textView= (TextView) wrongAnswerDialog.findViewById(R.id.textview_correct_answer);
        textView.setText("Ans : "+correctAnswer);

        btwrongAnswerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongAnswerDialog.dismiss();
                mquizActivity.setQuestionView();
            }
        });
        wrongAnswerDialog.show();
        wrongAnswerDialog.setCancelable(false);
        wrongAnswerDialog.setCanceledOnTouchOutside(false);

    }
}
