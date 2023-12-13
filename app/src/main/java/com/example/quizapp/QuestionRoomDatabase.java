package com.example.quizapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    private static QuestionRoomDatabase INSTANCE;

    public abstract QuestionDao questionDao();

    public static synchronized QuestionRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), QuestionRoomDatabase.class, "questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private QuestionDao questionDao;

        private PopulateDBAsyncTask(QuestionRoomDatabase db) {
            questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questionDao.insert(new Questions("What is Android?", "OS", "Database", "Browser", "Hardware", 1));
            questionDao.insert(new Questions("What is Chrome?", "OS", "Database", "Browser", "Hardware", 3));
            questionDao.insert(new Questions("What is Safari?", "OS", "Database", "Browser", "Hardware", 3));
            questionDao.insert(new Questions("What is Linux?", "OS", "Database", "Browser", "Hardware", 1));
            questionDao.insert(new Questions("What is UNIX?", "OS", "Database", "Browser", "Hardware", 1));
            questionDao.insert(new Questions("What is Firefox?", "OS", "Database", "Browser", "Hardware", 3));
//            questionDao.insert(new Questions("What is the latest version of Java?", "Java 1.8", "Java 2.0", "Java SE", "Java ME", 1));
//            questionDao.insert(new Questions("Which data type is used to store whole numbers in Java?", "int", "float", "double", "long", 1));
//            questionDao.insert(new Questions("What is the default value of a boolean variable in Java?", "null", "0", "FALSE", "1", 3));
//            questionDao.insert(new Questions("What is the purpose of the super keyword in Java?", "To call the constructor of the parent class", "To call the constructor of the child class", "To call the constructor of any class", "To call the constructor of the Object class", 1));
//            questionDao.insert(new Questions("Which collection class implements the Map interface in Java?",	"ArrayList",	"HashMap"	,"LinkedList",	"TreeMap",2));
//            questionDao.insert(new Questions("Which method is used to obtain the length of a string in Java?"	,"length()"	,"size()"	,"length"	,"size"	,1));
//            questionDao.insert(new Questions("What is the output of print(2 * 3 ** 3 * 4) in Python?",	"54"	,"108"	,"216",	"324",	4));
//            questionDao.insert(new Questions("Which of the following is a built-in function in Python?"	,"len()",	"size()",	"length()"	,"count()"	,1));
//            questionDao.insert(new Questions("Who is the father of Java?"	,"James Gosling"	,"Dennis Richie",	"Pemdas"	,"Gunda",	1));
//            questionDao.insert(new Questions("Which is a popular Java Integrated Development Environment (IDE)?", "BlueJ", "Eclipse", "IntelliJ IDEA", "NetBeans", 3));
            return null;
        }
    }
}
