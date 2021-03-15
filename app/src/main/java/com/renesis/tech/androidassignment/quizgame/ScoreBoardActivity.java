package com.renesis.tech.androidassignment.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;

public class ScoreBoardActivity extends AppCompatActivity {
    TextView id_1, id_2, id_3, id_4, id_5, id_6, id_7, id_8, id_9, id_10, id_11, id_12, id_13, id_14, id_15;
    String Score = "";
    ImageView back_btn;
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
        setContentView(R.layout.activity_score_board);
        init();
        backgroundMusic = BackgroundMusic.getInstance(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Score = null;
            } else {
                Score = extras.getString("current_score");
            }
        } else {
            Score = (String) savedInstanceState.getSerializable("current_score");
        }

        if (Score.equalsIgnoreCase("100"))
            id_1.setBackgroundResource(R.drawable.bg_green);
        else if (Score.equalsIgnoreCase("200")) {
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("300")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("500")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("1000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("2000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("4000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("8000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("16000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("32000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
            id_10.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("64000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
            id_10.setBackgroundResource(R.drawable.bg_green);
            id_11.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("125000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
            id_10.setBackgroundResource(R.drawable.bg_green);
            id_11.setBackgroundResource(R.drawable.bg_green);
            id_12.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("250000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
            id_10.setBackgroundResource(R.drawable.bg_green);
            id_11.setBackgroundResource(R.drawable.bg_green);
            id_12.setBackgroundResource(R.drawable.bg_green);
            id_13.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("500000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
            id_10.setBackgroundResource(R.drawable.bg_green);
            id_11.setBackgroundResource(R.drawable.bg_green);
            id_12.setBackgroundResource(R.drawable.bg_green);
            id_13.setBackgroundResource(R.drawable.bg_green);
            id_14.setBackgroundResource(R.drawable.bg_green);
        }
        else if (Score.equalsIgnoreCase("1000000")){
            id_1.setBackgroundResource(R.drawable.bg_green);
            id_2.setBackgroundResource(R.drawable.bg_green);
            id_3.setBackgroundResource(R.drawable.bg_green);
            id_4.setBackgroundResource(R.drawable.bg_green);
            id_5.setBackgroundResource(R.drawable.bg_green);
            id_6.setBackgroundResource(R.drawable.bg_green);
            id_7.setBackgroundResource(R.drawable.bg_green);
            id_8.setBackgroundResource(R.drawable.bg_green);
            id_9.setBackgroundResource(R.drawable.bg_green);
            id_10.setBackgroundResource(R.drawable.bg_green);
            id_11.setBackgroundResource(R.drawable.bg_green);
            id_12.setBackgroundResource(R.drawable.bg_green);
            id_13.setBackgroundResource(R.drawable.bg_green);
            id_14.setBackgroundResource(R.drawable.bg_green);
            id_15.setBackgroundResource(R.drawable.bg_green);
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        id_1 = findViewById(R.id.id_1);
        id_2 = findViewById(R.id.id_2);
        id_3 = findViewById(R.id.id_3);
        id_4 = findViewById(R.id.id_4);
        id_5 = findViewById(R.id.id_5);
        id_6 = findViewById(R.id.id_6);
        id_7 = findViewById(R.id.id_7);
        id_8 = findViewById(R.id.id_8);
        id_9 = findViewById(R.id.id_9);
        id_10 = findViewById(R.id.id_10);
        id_11 = findViewById(R.id.id_11);
        id_12 = findViewById(R.id.id_12);
        id_13 = findViewById(R.id.id_13);
        id_14 = findViewById(R.id.id_14);
        id_15 = findViewById(R.id.id_15);
        back_btn = findViewById(R.id.back_btn);
    }
}