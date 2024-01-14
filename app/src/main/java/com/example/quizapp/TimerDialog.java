package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerDialog {

    private Context mContext;
    private Dialog finalScoreDialog;



     TimerDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void timerDialog() {
        finalScoreDialog = new Dialog(mContext);
        finalScoreDialog.setContentView(R.layout.timer_dialog);
        final Button btFinalScoreDialog = (Button) finalScoreDialog.findViewById(R.id.bt_timer_dialog);


        btFinalScoreDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalScoreDialog.dismiss();
                Intent intent = new Intent(mContext, PlayActivity.class);
                mContext.startActivity(intent);
            }
        });
        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);
        finalScoreDialog.setCanceledOnTouchOutside(false);

    }

}
