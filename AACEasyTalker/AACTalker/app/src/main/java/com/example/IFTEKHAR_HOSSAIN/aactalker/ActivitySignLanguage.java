package com.example.ashik.aactalker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivitySignLanguage extends AppCompatActivity implements OnWordClick {
    RecyclerView recyclerView_sign, recyclerView_image ;
    SignAdapter adapter_sign;
    ImageAdapter adapter_image;
    List<Sign> imageList = new ArrayList<>();
    List<String> signList = new ArrayList<>();
    TextToSpeech t1;
    List<String> sentencesList = new ArrayList<>();

    ImageButton ibPlay,ibDelete,ibA,ibB,ibC,ibD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_language);

        ibDelete = (ImageButton) findViewById(R.id.ib_delete1);
        ibPlay = (ImageButton) findViewById(R.id.ib_play1);




        recyclerView_image = (RecyclerView) findViewById(R.id.rv_main_image);
        adapter_image = new ImageAdapter(imageList,this,this);
        recyclerView_image.setAdapter(adapter_image);
        recyclerView_image.setLayoutManager(new GridLayoutManager(this,2));

       recyclerView_sign = (RecyclerView) findViewById(R.id.rv_main_sign);
        adapter_sign = new SignAdapter(signList,this);
       recyclerView_sign.setAdapter(adapter_sign);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_sign.setLayoutManager(layoutManager);



        addImages();

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signList.size()>0){
                    signList.remove(signList.size()-1);
                    adapter_sign.notifyDataSetChanged();}
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s="";
                String[] sentence = new String[signList.size()];
                sentence = signList.toArray(sentence);

                for(int i =0;i<signList.size();i++){
                    s+=signList.get(i);
                }

                String toSpeak = s;
                // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    t1.setSpeechRate(1);
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,null);

                } else {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
               /* for (String s : sentence) {


                }*/
            }
        });

    }


    private void addImages() {
        //imageList.clear();;
        List<Sign> images = new Select().from(Sign.class).execute();
        if(images.size()>0)
            imageList.addAll(images);
        adapter_image.notifyDataSetChanged();
    }

    @Override
    public void onClick(Word word) {

    }

    public void onImageClick(Sign sign) {
        String toSpeak = sign.letter.toString();
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,null);
        } else {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }

        signList.add(sign.letter);
        adapter_sign.notifyDataSetChanged();

    }

    @Override
    public void onLetterClick(Letter letter) {

    }
}
