package com.renesis.tech.androidassignment.quizgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.NormalType.NormalGameTypeActivity;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

import io.paperdb.Paper;

public class GameModeActivity extends AppCompatActivity {
    ImageView backbtn;

    Button norml_btn, kid_btn;

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
        setContentView(R.layout.activity_game_mode);
        Paper.init(this);
        init();
        backgroundMusic = BackgroundMusic.getInstance(this);




        kid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("game_mode", "kid_mode");
                Intent intent = new Intent(GameModeActivity.this, NormalGameTypeActivity.class);
                startActivity(intent);
            }
        });

        norml_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("game_mode", "normal_mode");
                Intent intent = new Intent(GameModeActivity.this, NormalGameTypeActivity.class);
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
        backbtn = findViewById(R.id.backbtn);
        kid_btn = findViewById(R.id.kid_btn);
        norml_btn = findViewById(R.id.norml_btn);


    }

}