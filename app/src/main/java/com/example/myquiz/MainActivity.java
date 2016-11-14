package com.example.myquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences pref = this.getSharedPreferences("com.example.myquiz", Context.MODE_PRIVATE);
        int qtde = pref.getInt("qtde", 0);
        TextView qtdeText = (TextView)findViewById(R.id.main_testes_realizados);
        qtdeText.setText(qtde+"");
        float nota = pref.getFloat("nota", 0);
        TextView notaText = (TextView)findViewById(R.id.main_ultima_nota);
        notaText.setText(nota+"%");
        float soma = pref.getFloat("soma", 0);
        TextView mediaText = (TextView)findViewById(R.id.main_media);
        mediaText.setText((soma/qtde)+"%");
    }
    public void initTest(View view){
        Intent i = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(i);
    }
    public void initHistory(View view){
        Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(i);
    }
    public void loadListView(View view){
        Intent i = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(i);
    }
}
