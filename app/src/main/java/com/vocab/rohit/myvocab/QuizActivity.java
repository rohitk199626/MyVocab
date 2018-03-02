package com.vocab.rohit.myvocab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {


    Bundle bundle;
    ArrayList<String> sessionIds ;
    ArrayList<VisitWordMeaning> quizData ;
    VisitWordMeaning visitWordMeaning;
    DatabaseReference mref ;
    FirebaseDatabase database ;
    Random random;

    TextView questionTextView ;
    TextView statusTextView ;
    EditText answerEditText ;
    Button checkButton ;
    Button showAnswerButton ;
    Button nextButton ;

    int randomNum, numberOfQuestions, max,count;
    String userAnswer, answer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        bundle = getIntent().getExtras();
        sessionIds = bundle.getStringArrayList("SessionIds");

        for(String s : sessionIds)
        {
            Log.d("test", s);
        }

        quizData = new ArrayList<VisitWordMeaning>();

        database = FirebaseDatabase.getInstance();
        for(String sessionId : sessionIds)
        {
            mref = database.getReference().child(sessionId);
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    {
                        visitWordMeaning = new VisitWordMeaning();
                        visitWordMeaning.word = childSnapshot.getKey().toString();
                        visitWordMeaning.meaning = childSnapshot.getValue().toString();
                        Log.d("test",visitWordMeaning.word+" "+visitWordMeaning.meaning );
                        quizData.add(visitWordMeaning);
                        Log.d("test","" + quizData.size());
                    }
                    startQuiz();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public void startQuiz(){
        questionTextView = findViewById(R.id.questionTextView);
        statusTextView = findViewById(R.id.status);
        answerEditText = findViewById(R.id.answerEditText);
        checkButton = findViewById(R.id.checkAnswer);
        showAnswerButton = findViewById(R.id.showAnswerButton);
        nextButton = findViewById(R.id.nextButton);
        numberOfQuestions=quizData.size();
        max=numberOfQuestions;
        count=0;
        random = new Random();
        next();
    }


    public void next() {

        Log.d("count",""+count);
        if(count == numberOfQuestions)
        {
            Intent finishedActivity = new Intent(QuizActivity.this, FinishedQuiz.class);
            Log.d("count","starting activity");
            startActivity(finishedActivity);
            return;
        }

        answerEditText.setText("");
        statusTextView.setAlpha(0);
        while(true)
        {
            randomNum = random.nextInt(max);
            if(quizData.get(randomNum).visited==false)
            {
                break;
            }
        }
        questionTextView.setText(quizData.get(randomNum).meaning);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = answerEditText.getText().toString();
                answer = quizData.get(randomNum).word;
                if(userAnswer.toLowerCase().equals(answer.toLowerCase()))
                {
                    statusTextView.setText("That's Correct");
                    statusTextView.setTextColor(Color.GREEN);
                    statusTextView.setAlpha(1);
                    if(!quizData.get(randomNum).visited)
                        count++;
                    quizData.get(randomNum).visited = true ;

                }
                else
                {
                    statusTextView.setText("Oops! Wrong answer");
                    statusTextView.setTextColor(Color.RED);
                    statusTextView.setAlpha(1);
                }
            }
        });

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = quizData.get(randomNum).word;
                statusTextView.setText(answer);
                statusTextView.setTextColor(Color.GREEN);
                statusTextView.setAlpha(1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

}
