package com.example.quizapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class},version = 1)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    private static QuestionRoomDatabase INSTANCE;
    public abstract  QuestionDao questionDao();

    public static synchronized QuestionRoomDatabase getInstance(final Context context)
    {
        if (INSTANCE==null)
        {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),QuestionRoomDatabase.class,"questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback RoomDBCallback= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void>{

        private QuestionDao questionDao;

        private PopulateDBAsyncTask(QuestionRoomDatabase db){
            questionDao=db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questionDao.insert(new Questions("What is Android?","OS","Database","Browser","Hardware",1));
            questionDao.insert(new Questions("What is Chrome?","OS","Database","Browser","Hardware",3));
            questionDao.insert(new Questions("What is Safari?","OS","Database","Browser","Hardware",3));
            questionDao.insert(new Questions("What is Linux?","OS","Database","Browser","Hardware",1));
            questionDao.insert(new Questions("What is UNIX?","OS","Database","Browser","Hardware",1));
            questionDao.insert(new Questions("What is Firefox?","OS","Database","Browser","Hardware",3));
            return null;
        }
    }
}
