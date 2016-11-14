package com.example.myquiz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sergio on 12/11/2016.
 */

public class QuestionsSQLiteHelp extends SQLiteOpenHelper {
    public QuestionsSQLiteHelp(Context context) {super(context, "questions.db", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("" + "CREATE TABLE QUESTIONS(ID integer primary key autoincrement, QUESTION varchar NOT NULL, ANSWER varchar NOT NULL, OPTA varchar, OPTB varchar, OPTC varchar);" + "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS QUESTIONS");
        onCreate(db);
    }
}
