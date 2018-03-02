package com.vocab.rohit.myvocab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FinishedQuiz extends AppCompatActivity {

    Button homepageButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finished_quiz);

        Log.d("count","In Finishedquiz activity");
        homepageButton = findViewById(R.id.homepageButton);
        homepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinishedQuiz.this, MainActivity.class);
                startActivity(i);
                return;
            }
        });

    }
}
