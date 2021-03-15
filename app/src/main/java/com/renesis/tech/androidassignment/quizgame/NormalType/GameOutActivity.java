package com.renesis.tech.androidassignment.quizgame.NormalType;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renesis.tech.androidassignment.quizgame.BuildConfig;
import com.renesis.tech.androidassignment.quizgame.MainActivity;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;

import io.paperdb.Paper;

public class GameOutActivity extends AppCompatActivity {
    private String QuestionType;
    private String game_type;
    String current_money_back;
    Button play_again_btn, main_menu_btn,multiple_btn;
    TextView user_name;


BackgroundMusic backgroundMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_out);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                current_money_back = null;
            } else {
                QuestionType = extras.getString("question_type");
                game_type = extras.getString("game_type");
                current_money_back = extras.getString("current_money");
            }
        } else {

            current_money_back = (String) savedInstanceState.getSerializable("current_money");
            QuestionType = (String) savedInstanceState.getSerializable("question_type");
            game_type = (String) savedInstanceState.getSerializable("game_type");
        }
        backgroundMusic = BackgroundMusic.getInstance(this);
        backgroundMusic.Stopsound();
        backgroundMusic.Playsound(GameOutActivity.this, "you_lose.mp3");


        init();
        String user_name2 = Paper.book().read("user_name", "");
        if (!user_name2.equalsIgnoreCase("")) {
            user_name.setText("Try again " + user_name2);
        }


        play_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOutActivity.this, ArcadeModeActivity.class);
                intent.putExtra("question_type", QuestionType);
                intent.putExtra("game_type", game_type);
                startActivity(intent);
                finish();
            }
        });
        main_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOutActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        multiple_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Result:");
                    String shareMessage= "\nI won $ 0 while playing Quiz App\n\nDownload it from google play:\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivityForResult(Intent.createChooser(shareIntent, "choose one"),2);
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
    private void init() {
        user_name = findViewById(R.id.user_name);
        play_again_btn = findViewById(R.id.play_again_btn);
        main_menu_btn = findViewById(R.id.main_menu_btn);
        multiple_btn = findViewById(R.id.multiple_btn);
    }

    @Override
    public void onBackPressed() {

    }
}