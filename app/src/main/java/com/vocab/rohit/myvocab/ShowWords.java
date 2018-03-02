package com.vocab.rohit.myvocab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowWords extends AppCompatActivity {

    String word, meaning, session;
    ListView listView ;
    ArrayList<String> editWord ;
    ArrayList<WordMeaning> list = new ArrayList<WordMeaning>();
    DatabaseReference mref ;
    Bundle bundle ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_words);

        bundle = getIntent().getExtras();
        listView = findViewById(R.id.wordListView);
        session = bundle.getString("Session");
        mref = FirebaseDatabase.getInstance().getReference().child(session);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    word = childSnapshot.getKey().toString();
                    meaning = childSnapshot.getValue().toString();
                    WordMeaning wordMeaning = new WordMeaning(word, meaning);
                    list.add(wordMeaning);
                }
                    showDataOnListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editWord = new ArrayList<String>();
                editWord.add(session);
                editWord.add(list.get(position).word);
                editWord.add(list.get(position).meaning);
                Log.d("test",editWord.get(0)+" "+editWord.get(1)+" "+editWord.get(2));
                Intent i = new Intent(ShowWords.this, EditMeaning.class);
                i.putExtra("Extra", editWord);
                startActivity(i);
            }
        });

    }
    public void showDataOnListView()
    {
        WordMeaningAdapter wordMeaningAdapter = new WordMeaningAdapter(this, list);
        listView.setAdapter(wordMeaningAdapter);
    }
}
