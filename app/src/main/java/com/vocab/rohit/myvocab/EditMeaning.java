package com.vocab.rohit.myvocab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditMeaning extends AppCompatActivity {

    TextView editWord, editMeaning ;
    Button deleteButton, updateButton ;
    ArrayList<String> editWordData ;
    Bundle bundle ;
    String session, word, meaning;
    DatabaseReference mref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meaning);

        editWord = findViewById(R.id.editWord);
        editMeaning = findViewById(R.id.editMeaning);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        bundle = getIntent().getExtras() ;

        editWordData = bundle.getStringArrayList("Extra");

        session = editWordData.get(0);
        word = editWordData.get(1);
        meaning = editWordData.get(2);

        mref = FirebaseDatabase.getInstance().getReference().child(session);

        editWord.setText(word);
        editMeaning.setText(meaning);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mref.child(word).removeValue();
                finish();

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mref.child(word).removeValue();
                word = editWord.getText().toString();
                meaning = editMeaning.getText().toString();
                mref.child(word.toUpperCase()).setValue(meaning.toLowerCase());
                finish();
            }
        });
    }
}
