package com.renesis.tech.androidassignment.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.Utils.History;
import com.renesis.tech.androidassignment.quizgame.Utils.Utilities;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    ImageView menu_btn,no_ad;
    String checkvol, checkvid;
    Button newgamebtn, histry_btn, daily_btn, exit_btn;

    Bitmap profile_img = null;


    private FirebaseAnalytics mFirebaseAnalytics;

    BackgroundMusic backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        init();






        if (!Utils.isInternetAvailable_for_popup(this)){
            check_internet();
        }






        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Date current = null;
        try {
            current = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dialog();


            }
        });


        newgamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GameModeActivity.class);
                startActivity(intent);            }
        });
        histry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitdialog();
            }
        });








//
        backgroundMusic = BackgroundMusic.getInstance(this);
        backgroundMusic.playmusic_background(this, "main_menu.mp3");


//        playBeep();

    }
    private void check_internet() {
        final Dialog dialogexit = new Dialog(this);
        dialogexit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogexit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogexit.setCancelable(false);
        dialogexit.setContentView(R.layout.check_internet_dialog);


        final TextView retry = (TextView) dialogexit.findViewById(R.id.retry);
        final TextView exit = (TextView) dialogexit.findViewById(R.id.exit);


        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetAvailable_for_popup(MainActivity.this)){
                    dialogexit.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogexit.dismiss();
                finishAffinity();

            }
        });


        dialogexit.show();
    }

    @Override
    protected void onDestroy() {
        backgroundMusic.Stopmusic_background();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        backgroundMusic.pausemusic();
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        exitdialog();
    }





    private void exitdialog() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.exit_from_app_dialog);





        TextView yes = (TextView) dialog.findViewById(R.id.yes);
        TextView no = (TextView) dialog.findViewById(R.id.no);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });


        dialog.show();
    }




    @Override
    protected void onResume() {
        backgroundMusic.playmusic();

        super.onResume();
    }

    private void init() {
        menu_btn = findViewById(R.id.menu_btn);
        newgamebtn = findViewById(R.id.newgamebtn);
        histry_btn = findViewById(R.id.histry_btn);
        exit_btn = findViewById(R.id.exit_btn);
    }

    private void menu_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.menu_dialog);

        String check_sound = "";
        TextView okbtn = (TextView) dialog.findViewById(R.id.okbtn);
        final ImageView sound_btn = (ImageView) dialog.findViewById(R.id.sound_btn);
        final ImageView vibration_btn = (ImageView) dialog.findViewById(R.id.vibration_btn);
        final ImageView music_btn = (ImageView) dialog.findViewById(R.id.music_btn);

        final RelativeLayout help_btn = (RelativeLayout) dialog.findViewById(R.id.help_btn);
        final RelativeLayout share_btn = (RelativeLayout) dialog.findViewById(R.id.share_btn);
        final RelativeLayout about_btn = (RelativeLayout) dialog.findViewById(R.id.about_btn);




        check_sound = Utilities.getString(MainActivity.this, History.check_sound, "yes");
        if (check_sound.equalsIgnoreCase("yes")) {
            sound_btn.setImageResource(R.drawable.sound_on);
        } else {
            sound_btn.setImageResource(R.drawable.sound_off);
        }

        String check_music = Utilities.getString(MainActivity.this, History.check_music, "yes");
        if (check_music.equalsIgnoreCase("yes")) {
            music_btn.setImageResource(R.drawable.muzic_on);
        } else {
            music_btn.setImageResource(R.drawable.muzik_off);
        }

        checkvid = Utilities.getString(MainActivity.this, History.IS_VIB, "yes");
        if (checkvid.equalsIgnoreCase("yes")) {
            vibration_btn.setImageResource(R.drawable.vib_on);
        } else {
            vibration_btn.setImageResource(R.drawable.vib_off);
        }


        vibration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkvid = Utilities.getString(MainActivity.this, History.IS_VIB, "yes");
                if (checkvid.equalsIgnoreCase("yes")) {
                    vibration_btn.setImageResource(R.drawable.vib_off);
                    Utilities.saveString(MainActivity.this, History.IS_VIB, "no");
                } else {
                    vibration_btn.setImageResource(R.drawable.vib_on);
                    Utilities.saveString(MainActivity.this, History.IS_VIB, "yes");
                }
            }
        });


        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_sound = Utilities.getString(MainActivity.this, History.check_sound, "yes");
                if (check_sound.equalsIgnoreCase("yes")) {
                    backgroundMusic.Stopsound();
                    sound_btn.setImageResource(R.drawable.sound_off);
                    Utilities.saveString(MainActivity.this, History.check_sound, "no");
                } else {
                    sound_btn.setImageResource(R.drawable.sound_on);
                    Utilities.saveString(MainActivity.this, History.check_sound, "yes");
                }
            }
        });

        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_sound = Utilities.getString(MainActivity.this, History.check_music, "yes");
                if (check_sound.equalsIgnoreCase("yes")) {
                    backgroundMusic.Stopmusic_background();
                    music_btn.setImageResource(R.drawable.muzik_off);
                    Utilities.saveString(MainActivity.this, History.check_music, "no");
                } else {
                    music_btn.setImageResource(R.drawable.muzic_on);

                    Utilities.saveString(MainActivity.this, History.check_music, "yes");
                    backgroundMusic.playmusic_background(MainActivity.this, "main_menu.mp3");
                }
            }
        });


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://familiaapps2019.blogspot.com/2020/08/quiz-privacy-policy.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz App");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });


        dialog.show();
    }


}