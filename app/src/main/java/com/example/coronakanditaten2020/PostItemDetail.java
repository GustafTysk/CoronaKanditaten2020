package com.example.coronakanditaten2020;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PostItemDetail extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postview);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        System.out.println(position);

        // Here we turn your string.xml in an array
        // String[] myKeys = getResources().getStringArray(R.array.sections);

        TextView myTextView = (TextView) findViewById(R.id.my_textview);
        //myTextView.setText(myKeys[position]);


    }
}
