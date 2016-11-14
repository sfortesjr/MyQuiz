package com.example.myquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquiz.db.QuestionsSQLiteHelp;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestActivity extends AppCompatActivity {
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> userAnswers = new ArrayList<>();
    private int position = 0;
    private int qid = 0;
    QuestionsSQLiteHelp dbHelper = null;
    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        createTest();
        clearAnswers();
        refreshQuestion();
    }
    private void createTest(){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM QUESTIONS;", null);
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                Question q = new Question();
                q.setID(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOption1(cursor.getString(3));
                q.setOption2(cursor.getString(4));
                q.setOption3(cursor.getString(5));
                questions.add(q);
            }
        }
    }
    private void clearAnswers(){
        for (Question question: questions){
            userAnswers.add("");
        }
    }
    private void refreshQuestion(){
        Question q = questions.get(position);
        TextView posTextView = (TextView)findViewById(R.id.positionTextView);
        posTextView.setText((position+1)+" de "+questions.size());
        Question currentQ = questions.get(qid);
        TextView qTextView = (TextView)findViewById(R.id.questionTextView);
        qTextView.setText(currentQ.getQuestion);
        RadioButton opt1 = (RadioButton)findViewById(R.id.option1Radio);
        opt1.setText(currentQ.getOption1);
        RadioButton opt2 = (RadioButton)findViewById(R.id.option2Radio);
        opt2.setText(currentQ.getOption2);
        RadioButton opt3 = (RadioButton)findViewById(R.id.option3Radio);
        opt3.setText(currentQ.getOption3);
        RadioGroup group = (RadioGroup)findViewById(R.id.optionGroup);
        group.check(0);
        if (userAnswers.get(position).equals(opt1.getText()))
            group.check(R.id.option1Radio);
        else if (userAnswers.get(position).equals(opt2.getText()))
            group.check(R.id.option2Radio);
        else if (userAnswers.get(position).equals(opt3.getText()))
            group.check(R.id.option3Radio);
        qid++;
    }
    public void back(View view){
        if (position>0){
            position--;
            refreshQuestion();
        }
    }
    public void next(View view){
        if (position<questions.size()-1){
            position++;
            refreshQuestion();
        }
    }
    public void optionSelection(View view){
        RadioButton opt = (RadioButton)findViewById(view.getId());
        userAnswers.set(position, opt.getText().toString());
    }
    public void finish(View view){
        int sum = 0;
        for (int i=0; i<questions.size(); i++){
            if (questions.get(i).answer.equals(userAnswers.get(i))){
                sum++;
            }
        }
        double result = 100.0 * (double)sum / (double)questions.size();

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String filename = date + ": [" + result + "%]";
        FileOutputStream output;
        try {
            output = openFileOutput(filename, Context.MODE_PRIVATE);
            output.write(("Nota: "+result+"%\n\n").getBytes());
            for (int i=0; i<questions.size(); i++){
                output.write(questions.get(i).question.getBytes());
                output.write(": ".getBytes());
                output.write(userAnswers.get(i).getBytes());
                output.write("\n".getBytes());
                if (questions.get(i).answer.equals(userAnswers.get(i))){
                    output.write("CORRETA!\n\n".getBytes());
                }else {
                    output.write(("INCORRETA! ("+questions.get(i).answer+")\n\n").getBytes());
                }
            }
            output.close();
        }catch (Exception ex){
            Toast.makeText(this, "Erro ao gravar o arquivo: "+ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        SharedPreferences pref = this.getSharedPreferences("com.examples.myquiz", Context.MODE_PRIVATE);
        int qtde = pref.getInt("qtde", 0)+1;
        float soma = (float) (pref.getFloat("soma", 0)+result);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("qtde", qtde);
        editor.putFloat("soma", soma);
        editor.putFloat("nota", (float)result);
        editor.commit();

        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
        i.putExtra("result", result);
        startActivity(i);
        finish();
    }
}
