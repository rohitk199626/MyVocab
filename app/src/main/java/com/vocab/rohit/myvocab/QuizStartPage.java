package com.vocab.rohit.myvocab;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizStartPage extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> list ;
    ArrayList<String> sessionIds ;
    DatabaseReference mref ;
    FloatingActionButton fab ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_start_page);

        listView = findViewById(R.id.quizListView);
        list = new ArrayList<String>();
        sessionIds = new ArrayList<String>();
        fab = findViewById(R.id.quizFloatingButton);

        mref = FirebaseDatabase.getInstance().getReference();
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                list.add(dataSnapshot.getKey().toString());
                showDataOnListView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getKey().toString());
                showDataOnListView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.isEnabled()==false)
                {
                    view.setEnabled(true);
                    view.setBackgroundColor(Color.CYAN);
                    sessionIds.add(parent.getItemAtPosition(position).toString());
                }
                else
                {
                    view.setEnabled(false);
                    view.setBackgroundColor(Color.WHITE);
                    sessionIds.remove(parent.getItemAtPosition(position).toString());
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizStartPage.this, QuizActivity.class);
                i.putExtra("SessionIds", sessionIds);
                startActivity(i);
            }
        });

    }
    public void showDataOnListView()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.my_custom_layout, list);
        listView.setAdapter(adapter);
    }


}
