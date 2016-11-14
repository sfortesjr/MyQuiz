package com.example.myquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        double result = i.getDoubleExtra("result", 0);

        TextView resultTextView = (TextView)findViewById(R.id.resultTextView);
        resultTextView.setText(result+"%");
    }
    public void restart(View view){
        finish();
    }
}
