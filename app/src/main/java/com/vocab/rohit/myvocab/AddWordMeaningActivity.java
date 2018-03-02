package com.vocab.rohit.myvocab;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWordMeaningActivity extends AppCompatActivity {

    Button addButton ;
    EditText editText ;
    String sessionId, word, meaning ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_meaning);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText = findViewById(R.id.sessionId);
                sessionId = "Session: " + editText.getText().toString();
                editText = findViewById(R.id.word);
                word = editText.getText().toString().toUpperCase();
                editText.setText("");
                editText = findViewById(R.id.meaning);
                meaning = editText.getText().toString();
                editText.setText("");
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference(sessionId);
                dr.child(word).setValue(meaning);
                Toast.makeText(getApplicationContext(),"Successfully Added",Toast.LENGTH_LONG).show();
            }
        });

    }
}
