package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CorrectDialog {

    private Context mContext;
    private Dialog correctDialog;

    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void correctDialog(int score) {
        correctDialog = new Dialog(mContext);
        correctDialog.setContentView(R.layout.correct_dialog);
        final Button btcorrectDialog = (Button) correctDialog.findViewById(R.id.bt_score_dialog);
        score(score);

        btcorrectDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctDialog.dismiss();
            }
        });
        correctDialog.show();
        correctDialog.setCancelable(false);
        correctDialog.setCanceledOnTouchOutside(false);

    }

    private void score(int score) {
        TextView textView=(TextView) correctDialog.findViewById(R.id.textview_score);
        textView.setText("Score: "+String.valueOf(score));
    }


}
