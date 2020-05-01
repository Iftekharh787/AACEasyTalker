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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnWordClick{

    List<Word> wordList = new ArrayList<>();

    List<String> sentenceList = new ArrayList<>();

    //List<String> sentence = new ArrayList<>();
    RecyclerView recyclerView_word,recyclerView_sentence ;
    WordAdapter adapter_word;
    SentenceAdapter adapter_sentence;
    TextView tvWord;
    TextToSpeech t1;
    ImageButton ibPlay,ibAdd,ibDelete;
    Button btnVerb,btnSubject,btnObject,btnOthers,btnSign,btnLetter;
    String parent="None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Word.class,Sign.class,Letter.class);
        ActiveAndroid.initialize(config.create());
        checkFirstRun();

        ibAdd = (ImageButton) findViewById(R.id.ib_add);
        ibDelete = (ImageButton) findViewById(R.id.ib_delete);
        ibPlay = (ImageButton) findViewById(R.id.ib_play);
        tvWord = (TextView) findViewById(R.id.tv_word);
        btnVerb = (Button) findViewById(R.id.btn_verb);
        btnSubject = (Button) findViewById(R.id.btn_subject);
        btnObject = (Button) findViewById(R.id.btn_object);
        btnOthers = (Button) findViewById(R.id.btn_others);
        btnSign = (Button) findViewById(R.id.btn_sign);
        btnLetter = (Button) findViewById(R.id.btn_letter);

        recyclerView_word = (RecyclerView) findViewById(R.id.rv_main);
        adapter_word = new WordAdapter(wordList,this,this);
        recyclerView_word.setAdapter(adapter_word);
        recyclerView_word.setLayoutManager(new GridLayoutManager(this,3));

        recyclerView_sentence = (RecyclerView) findViewById(R.id.rv_main_sentence);
        adapter_sentence = new SentenceAdapter(sentenceList,this,this);
        recyclerView_sentence.setAdapter(adapter_sentence);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_sentence.setLayoutManager(layoutManager);



        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parent=="None"){
                    Toast.makeText(MainActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ActivityAddWord.class);
                    intent.putExtra("string", parent.toString());
                    startActivity(intent);
                }
            }
        });

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sentenceList.size()>0){
                sentenceList.remove(sentenceList.size()-1);
                adapter_sentence.notifyDataSetChanged();}
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s="";
                String[] sentence = new String[sentenceList.size()];
                sentence = sentenceList.toArray(sentence);

                for(int i =0;i<sentenceList.size();i++){
                    s+=" "+sentenceList.get(i);
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

        btnSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent="Subject";
                wordList.clear();
                List<Word> words = new Select().from(Word.class).where("parentName=?","Subject").execute();
                wordList.addAll(words);
                adapter_word.notifyDataSetChanged();
            }
        });

        btnVerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent="Verb";
                wordList.clear();
                List<Word> words = new Select().from(Word.class).where("parentName=?","Verb").execute();
                wordList.addAll(words);
                adapter_word.notifyDataSetChanged();
            }
        });

        btnObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent="Object";
                wordList.clear();
                List<Word> words = new Select().from(Word.class).where("parentName=?","Object").execute();
                wordList.addAll(words);
                adapter_word.notifyDataSetChanged();
            }
        });

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent="Others";
                wordList.clear();
                List<Word> words = new Select().from(Word.class).where("parentName=?","Others").execute();
                wordList.addAll(words);
                adapter_word.notifyDataSetChanged();
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivitySignLanguage.class);
                startActivity(intent);
            }
        });

        btnLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityTextToSign.class);
                startActivity(intent);
            }
        });


    }
    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            Word w1 = new Word("Subject","I");
            w1.save();
            Word w2 = new Word("Subject","We");
            w2.save();
            Word w5 = new Word("Subject","He");
            w5.save();
            Word w6 = new Word("Subject","She");
            w6.save();
            Word w7 = new Word("Subject","You");
            w7.save();
            Word w3 = new Word("Verb","Want");
            w3.save();
            Word w4 = new Word("Verb","Eat");
            w4.save();
            Word w8 = new Word("Verb","Need");
            w8.save();
            Word w9 = new Word("Verb","Like");
            w9.save();
            Word w10 = new Word("Verb","Have");
            w10.save();
            Word w14 = new Word("Verb","Go");
            w14.save();
            Word w11 = new Word("Object","Food");
            w11.save();
            Word w12 = new Word("Object","Dress");
            w12.save();
            Word w13 = new Word("Object","Water");
            w13.save();
            Word w15 = new Word("Object","Lunch");
            w15.save();
            Word w16 = new Word("Object","Dinner");
            w16.save();
            Word w17 = new Word("Object","Home");
            w17.save();
            Word w18 = new Word("Object","School");
            w18.save();
            Word w19 = new Word("Others","To");
            w19.save();
            Word w20 = new Word("Others","A");
            w20.save();
            Word w21 = new Word("Others","An");
            w21.save();
            Word w22 = new Word("Others","The");
            w22.save();
            Word w23 = new Word("Others","Will");
            w23.save();
            Word w24 = new Word("Others","In");
            w24.save();


            Sign sign = new Sign();
            sign.letter = "a";
            sign.image = R.drawable.a;
            sign.save();

            Sign sign1 = new Sign();
            sign1.letter = "b";
            sign1.image = R.drawable.b;
            sign1.save();

            Sign sign2 = new Sign();
            sign2.letter = "c";
            sign2.image = R.drawable.c;
            sign2.save();

            Sign sign3 = new Sign();
            sign3.letter = "d";
            sign3.image = R.drawable.d;
            sign3.save();

            Sign sign4 = new Sign();
            sign4.letter = "e";
            sign4.image = R.drawable.e;
            sign4.save();

            Sign sign5 = new Sign();
            sign5.letter = "f";
            sign5.image = R.drawable.f;
            sign5.save();

            Sign sign6 = new Sign();
            sign6.letter = "g";
            sign6.image = R.drawable.g;
            sign6.save();

            Sign sign7 = new Sign();
            sign7.letter = "h";
            sign7.image = R.drawable.h;
            sign7.save();

            Sign sign8 = new Sign();
            sign8.letter = "i";
            sign8.image = R.drawable.i;
            sign8.save();

            Sign sign9 = new Sign();
            sign9.letter = "j";
            sign9.image = R.drawable.j;
            sign9.save();

            Sign sign10 = new Sign();
            sign10.letter = "k";
            sign10.image = R.drawable.k;
            sign10.save();

            Sign sign11 = new Sign();
            sign11.letter = "l";
            sign11.image = R.drawable.l;
            sign11.save();

            Sign sign12 = new Sign();
            sign12.letter = "m";
            sign12.image = R.drawable.m;
            sign12.save();

            Sign sign13 = new Sign();
            sign13.letter = "n";
            sign13.image = R.drawable.n;
            sign13.save();

            Sign sign14 = new Sign();
            sign14.letter = "o";
            sign14.image = R.drawable.o;
            sign14.save();

            Sign sign15 = new Sign();
            sign15.letter = "p";
            sign15.image = R.drawable.p;
            sign15.save();

            Sign sign16 = new Sign();
            sign16.letter = "q";
            sign16.image = R.drawable.q;
            sign16.save();

            Sign sign17 = new Sign();
            sign17.letter = "r";
            sign17.image = R.drawable.r;
            sign17.save();

            Sign sign18 = new Sign();
            sign18.letter = "s";
            sign18.image = R.drawable.s;
            sign18.save();

            Sign sign19 = new Sign();
            sign19.letter = "t";
            sign19.image = R.drawable.t;
            sign19.save();

            Sign sign20 = new Sign();
            sign20.letter = "u";
            sign20.image = R.drawable.u;
            sign20.save();

            Sign sign21 = new Sign();
            sign21.letter = "v";
            sign21.image = R.drawable.v;
            sign21.save();

            Sign sign22 = new Sign();
            sign22.letter = "w";
            sign22.image = R.drawable.w;
            sign22.save();

            Sign sign23 = new Sign();
            sign23.letter = "x";
            sign23.image = R.drawable.x;
            sign23.save();

            Sign sign24 = new Sign();
            sign24.letter = "y";
            sign24.image = R.drawable.y;
            sign24.save();

            Sign sign25 = new Sign();
            sign25.letter = "z";
            sign25.image = R.drawable.z;
            sign25.save();

            Sign sign26 = new Sign();
            sign26.letter = " ";
            sign26.image = R.drawable.space1;
            sign26.save();


            Letter l = new Letter();
            l.letters ="A";
            l.img = R.drawable.a;
            l.save();

            Letter l1 = new Letter();
            l1.letters ="B";
            l1.img = R.drawable.b;
            l1.save();

            Letter l3 = new Letter();
            l3.letters ="C";
            l3.img = R.drawable.c;
            l3.save();

            Letter l4 = new Letter();
            l4.letters ="D";
            l4.img = R.drawable.d;
            l4.save();

            Letter l5 = new Letter();
            l5.letters ="E";
            l5.img = R.drawable.e;
            l5.save();

            Letter l6 = new Letter();
            l6.letters ="F";
            l6.img = R.drawable.f;
            l6.save();

            Letter l7 = new Letter();
            l7.letters ="G";
            l7.img = R.drawable.g;
            l7.save();

            Letter l8 = new Letter();
            l8.letters ="H";
            l8.img = R.drawable.h;
            l8.save();

            Letter l9 = new Letter();
            l9.letters ="I";
            l9.img = R.drawable.i;
            l9.save();

            Letter l10 = new Letter();
            l10.letters ="J";
            l10.img = R.drawable.j;
            l10.save();

            Letter l11 = new Letter();
            l11.letters ="K";
            l11.img = R.drawable.k;
            l11.save();

            Letter l12 = new Letter();
            l12.letters ="L";
            l12.img = R.drawable.l;
            l12.save();

            Letter l13 = new Letter();
            l13.letters ="M";
            l13.img = R.drawable.m;
            l13.save();

            Letter l14 = new Letter();
            l14.letters ="N";
            l14.img = R.drawable.n;
            l14.save();

            Letter l15 = new Letter();
            l15.letters ="O";
            l15.img = R.drawable.o;
            l15.save();

            Letter l16 = new Letter();
            l16.letters ="P";
            l16.img = R.drawable.p;
            l16.save();

            Letter l17 = new Letter();
            l17.letters ="Q";
            l17.img = R.drawable.q;
            l17.save();

            Letter l18 = new Letter();
            l18.letters ="R";
            l18.img = R.drawable.r;
            l18.save();

            Letter l19 = new Letter();
            l19.letters ="S";
            l19.img = R.drawable.s;
            l19.save();

            Letter l20 = new Letter();
            l20.letters ="T";
            l20.img = R.drawable.t;
            l20.save();

            Letter l21 = new Letter();
            l21.letters ="U";
            l21.img = R.drawable.u;
            l21.save();

            Letter l22 = new Letter();
            l22.letters ="V";
            l22.img = R.drawable.v;
            l22.save();

            Letter l23 = new Letter();
            l23.letters ="W";
            l23.img = R.drawable.w;
            l23.save();

            Letter l24 = new Letter();
            l24.letters ="X";
            l24.img = R.drawable.x;
            l24.save();

            Letter l25 = new Letter();
            l25.letters ="Y";
            l25.img = R.drawable.y;
            l25.save();

            Letter l26 = new Letter();
            l26.letters ="Z";
            l26.img = R.drawable.z;
            l26.save();

            Letter l27 = new Letter();
            l27.letters ="Space";
            l27.img = R.drawable.space;
            l27.save();



            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    private void addWords() {
        List<Word> words = new Select().from(Word.class).execute();
        if(words.size()>0)
            wordList.addAll(words);
    }

    @Override
    public void onClick(Word word) {
        String toSpeak = word.word.toString();
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null,null);
        } else {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }

        sentenceList.add(word.word);
        adapter_sentence.notifyDataSetChanged();

    }

    @Override
    public void onImageClick(Sign sign) {

    }

    @Override
    public void onLetterClick(Letter letter) {

    }
}
