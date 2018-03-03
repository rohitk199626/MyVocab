package com.vocab.rohit.myvocab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWithoutSession extends AppCompatActivity {

    EditText wordInput ;
    EditText meaningInput ;
    Button addButton ;
    String session, word, meaning ;
    Bundle bundle;
    DatabaseReference dref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_without_session);

        wordInput = findViewById(R.id.wordInput);
        meaningInput = findViewById(R.id.meaningInput);
        addButton = findViewById(R.id.addButtonWithoutSession);
        bundle = getIntent().getExtras();

        session = bundle.getString("Session");

        dref = FirebaseDatabase.getInstance().getReference().child(session);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = wordInput.getText().toString();
                meaning = meaningInput.getText().toString();
                dref.child(word.toUpperCase()).setValue(meaning);
                wordInput.setText("");
                meaningInput.setText("");
                Toast.makeText(getApplicationContext(),"Successfully Added",Toast.LENGTH_LONG).show();
            }
        });

    }
}
