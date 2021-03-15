package com.renesis.tech.androidassignment.quizgame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renesis.tech.androidassignment.quizgame.NormalType.ArcadeModeActivity;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

import java.text.NumberFormat;
import java.util.Locale;

import io.paperdb.Paper;

public class GameOverActivity extends AppCompatActivity {
    TextView current_money;
    String current_money_back;
    Button play_again_btn, main_menu_btn,multiple_btn;
    private String QuestionType;
    private String game_type;



    BackgroundMusic backgroundMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


        init();

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
        current_money.setText("Your Score: $ " + NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(current_money_back)));

        if (!game_type.equalsIgnoreCase("timed")) {
            String last_Score = Paper.book().read("high_score_arcade", "0");
            if (Integer.parseInt(last_Score) < Integer.parseInt(current_money_back)) {
                Paper.book().write("high_score_arcade", current_money_back);
            }
        } else {
            String last_Score = Paper.book().read("high_score_time", "0");
            if (Integer.parseInt(last_Score) < Integer.parseInt(current_money_back)) {
                Paper.book().write("high_score_time", current_money_back);
            }

        }

        backgroundMusic = BackgroundMusic.getInstance(this);
        backgroundMusic.Stopsound();
        backgroundMusic.Playsound(GameOverActivity.this, "some_money.mp3");


        play_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, ArcadeModeActivity.class);
                intent.putExtra("question_type", QuestionType);
                intent.putExtra("game_type", game_type);
startActivity(intent);
                finish();
            }
        });
        main_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
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
                    String shareMessage= "I won $ "+NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(current_money_back))+" while playing Quiz App\n\nDownload it from google play:\n";
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
        current_money = findViewById(R.id.current_money);
        play_again_btn = findViewById(R.id.play_again_btn);
        main_menu_btn = findViewById(R.id.main_menu_btn);
        multiple_btn = findViewById(R.id.multiple_btn);
    }

    @Override
    public void onBackPressed() {

    }
}