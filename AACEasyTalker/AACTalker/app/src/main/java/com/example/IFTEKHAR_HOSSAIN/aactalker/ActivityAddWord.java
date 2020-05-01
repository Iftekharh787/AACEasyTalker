package com.example.ashik.aactalker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

public class ActivityAddWord extends AppCompatActivity {
    EditText newWord;
    Button btnAddWord;
    String string1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_word);

        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Word.class);
        ActiveAndroid.initialize(config.create());

        newWord = (EditText) findViewById(R.id.new_word);
        btnAddWord = (Button) findViewById(R.id.add_word);

        Intent intent2 = getIntent();
        string1 = intent2.getStringExtra("string");

        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word w1 = new Word(string1.toString(),newWord.getText().toString());
                w1.save();
                Toast.makeText(ActivityAddWord.this, "Added "+newWord.getText().toString()+" to "+string1.toString(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ActivityAddWord.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }
}
