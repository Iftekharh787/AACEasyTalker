package com.example.ashik.aactalker;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityTextToSign extends AppCompatActivity implements OnWordClick {
    RecyclerView recyclerView_letter, recyclerView_tti ;
    LetterAdapter adapter_letter;
    TextToImageAdapter adapter_tti;
    List<Letter> letterList = new ArrayList<>();
    List<Integer> ttiList = new ArrayList<>();
    TextToSpeech t1;
    List<String> sentencesList = new ArrayList<>();

    ImageButton ibPlay,ibDelete2,ibA,ibB,ibC,ibD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_text_to_sign);

        ibDelete2 = (ImageButton) findViewById(R.id.ib_delete2);

        recyclerView_letter = (RecyclerView) findViewById(R.id.rv_main_letter);
        adapter_letter = new LetterAdapter(letterList,this,this);
        recyclerView_letter.setAdapter(adapter_letter);
        recyclerView_letter.setLayoutManager(new GridLayoutManager(this,3));

        recyclerView_tti = (RecyclerView) findViewById(R.id.rv_main_texttoimage);
        adapter_tti = new TextToImageAdapter(ttiList,this,this);
        recyclerView_tti.setAdapter(adapter_tti);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_tti.setLayoutManager(layoutManager);



        addLetters();

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        ibDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ttiList.size()>0){
                    ttiList.remove(ttiList.size()-1);
                    adapter_tti.notifyDataSetChanged();}
            }
        });
    }

    private void addLetters() {
        List<Letter> letters = new Select().from(Letter.class).execute();
        if(letters.size()>0)
            letterList.addAll(letters);
    }

    @Override
    public void onClick(Word word) {

    }

    @Override
    public void onImageClick(Sign sign) {

    }

    @Override
    public void onLetterClick(Letter letter) {
        String toSpeak = letter.letters.toString();
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,null);
        } else {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }

        ttiList.add(letter.img);
        adapter_tti.notifyDataSetChanged();


    }
}
