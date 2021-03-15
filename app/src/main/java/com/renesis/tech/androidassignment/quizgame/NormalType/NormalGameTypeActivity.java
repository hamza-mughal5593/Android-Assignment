package com.renesis.tech.androidassignment.quizgame.NormalType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.renesis.tech.androidassignment.quizgame.CategoryType.CategoryListActivity;
import com.renesis.tech.androidassignment.quizgame.QustionTypeActivity;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class NormalGameTypeActivity extends AppCompatActivity {

    CircleImageView profile_image;
    ImageView backbtn;
    RelativeLayout  timed_btn, category_btn;



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
        setContentView(R.layout.activity_normal_game_type);
        Paper.init(this);

        backgroundMusic = BackgroundMusic.getInstance(this);




        init();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        timed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NormalGameTypeActivity.this, QustionTypeActivity.class);
                intent.putExtra("game_type", "timed");
                startActivity(intent);
            }
        });
        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NormalGameTypeActivity.this, CategoryQuestionActivity.class);
                Intent intent = new Intent(NormalGameTypeActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });
    }



    private void init() {

        profile_image = findViewById(R.id.profile_image);
        backbtn = findViewById(R.id.backbtn);
        timed_btn = findViewById(R.id.timed_btn);
        category_btn = findViewById(R.id.category_btn);

    }


}