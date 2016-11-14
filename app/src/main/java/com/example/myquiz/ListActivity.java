package com.example.myquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myquiz.db.QuestionsSQLiteHelp;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    QuestionsSQLiteHelp dbHelper = null;
    SQLiteDatabase db = null;
    ArrayAdapter<String> questionsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<String> questionList = new ArrayList<>();
        questionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionList);
        ListView listView = (ListView)findViewById(R.id.questionsListView);
        listView.setAdapter(questionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        try {
            dbHelper = new QuestionsSQLiteHelp(getApplicationContext());
            refreshQuestions();
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getLocalizedMessage()).setPositiveButton("Ok", null).show();
        }
    }
    protected void refreshQuestions(){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM QUESTIONS", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String nome = cursor.getString(1);
            questionsAdapter.add(nome);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        dbHelper.close();
    }
    public void addQuestion(View view){
        try {
            EditText q = (EditText)findViewById(R.id.newQuestionEditText);
            EditText a = (EditText)findViewById(R.id.newAnswerEditText);
            EditText o1 = (EditText)findViewById(R.id.newOption1EditText);
            EditText o2 = (EditText)findViewById(R.id.newOption2EditText);
            EditText o3 = (EditText)findViewById(R.id.newOption3EditText);
            String question = q.getText().toString();
            String answer = a.getText().toString();
            String option1 = o1.getText().toString();
            String option2 = o2.getText().toString();
            String option3 = o3.getText().toString();
            db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO QUESTIONS(QUESTION, ANSWER, OPTA, OPTB, OPTC) VALUES('"+question+"', '"+answer+"', '"+option1+"', '"+option2+"', '"+option3+"')");
            db.close();
            dbHelper.close();
            q.setText("");
            a.setText("");
            o1.setText("");
            o2.setText("");
            o3.setText("");
            questionsAdapter.add(question);
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getLocalizedMessage()).setPositiveButton("Ok", null).show();
        }
    }
    public void deleteQuestion(String question){
        try {
            db = dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM QUESTIONS WHERE QUESTION = '"+question+"'");
            db.close();
            dbHelper.close();
            questionsAdapter.remove(question);
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getLocalizedMessage()).setPositiveButton("Ok", null).show();
        }
    }
    public void initTest(View view){
        Intent i = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(i);
    }
}
