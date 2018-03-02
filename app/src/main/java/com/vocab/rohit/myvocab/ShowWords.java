package com.vocab.rohit.myvocab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    String word, meaning;
    ListView listView ;
    ArrayList<WordMeaning> list = new ArrayList<WordMeaning>();
    DatabaseReference mref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_words);

        Bundle bundle = getIntent().getExtras();
        listView = findViewById(R.id.wordListView);
        Log.d("Session", bundle.getString("Session"));
        mref = FirebaseDatabase.getInstance().getReference().child(bundle.getString("Session"));
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

    }
    public void showDataOnListView()
    {
        WordMeaningAdapter wordMeaningAdapter = new WordMeaningAdapter(this, list);
        listView.setAdapter(wordMeaningAdapter);
    }
}
