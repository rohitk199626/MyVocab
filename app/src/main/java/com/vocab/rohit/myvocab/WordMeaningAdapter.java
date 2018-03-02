package com.vocab.rohit.myvocab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class WordMeaningAdapter extends ArrayAdapter<WordMeaning> {

    TextView wordTextView ;
    TextView meaningTextView ;

    public WordMeaningAdapter(@NonNull Context context, ArrayList<WordMeaning> list) {
        super(context, R.layout.word_meaning_layout, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.word_meaning_layout, parent, false);

        wordTextView = v.findViewById(R.id.wordTextView);
        meaningTextView = v.findViewById(R.id.meaningTextView);

        WordMeaning wordMeaning = getItem(position);

        wordTextView.setText(wordMeaning.word);
        meaningTextView.setText(wordMeaning.meaning);

        return v ;
    }
}
