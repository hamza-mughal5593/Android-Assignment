package com.renesis.tech.androidassignment.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.renesis.tech.androidassignment.quizgame.NormalType.ArcadeModeActivity;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

public class QustionTypeActivity extends AppCompatActivity {
Button multiple_btn,boolean_btn;
String game_type;
ImageView backbtn;


    BackgroundMusic backgroundMusic;


    @Override
    protected void onPause() {
        backgroundMusic.pausemusic();
        super.onPause();
    }
    @Override
    protected void onResume() {
        backgroundMusic.playmusic();
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qustion_type);


        backgroundMusic = BackgroundMusic.getInstance(this);





        init();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                game_type = null;
            } else {
                game_type = extras.getString("game_type");
            }
        } else {
            game_type = (String) savedInstanceState.getSerializable("game_type");
        }
        multiple_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundMusic.Stopmusic_background();

                Intent intent = new Intent(QustionTypeActivity.this, ArcadeModeActivity.class);
                intent.putExtra("question_type","multiple");
                intent.putExtra("game_type",game_type);
                startActivity(intent);
            }
        });
        boolean_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundMusic.Stopmusic_background();

                Intent intent = new Intent(QustionTypeActivity.this, ArcadeModeActivity.class);
                intent.putExtra("question_type","boolean");
                intent.putExtra("game_type",game_type);
                startActivity(intent);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void init() {
        multiple_btn=findViewById(R.id.multiple_btn);
        boolean_btn=findViewById(R.id.boolean_btn);
        backbtn=findViewById(R.id.backbtn);


    }
}