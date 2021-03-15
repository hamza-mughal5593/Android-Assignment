package com.renesis.tech.androidassignment.quizgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renesis.tech.androidassignment.quizgame.Adapter.HistoryAdapter;
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryListModel;
import com.renesis.tech.androidassignment.quizgame.Pojo.HistoryModel;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HistoryActivity extends AppCompatActivity {
    String high_score_arcade_data, high_score_time_data;
    TextView total_asked_question, total_correct_ans, high_score_arcade, high_score_time, best_time, game_complt, daily_badges;
    HistoryAdapter historyAdapter;
    ImageView backbtn, share_histy;
    RecyclerView cate_histry_RV;
    ArrayList<HistoryModel> historyModelArrayList;
    int total_cat_percentage = 0;
    ArrayList<CategoryListModel> categoryList;
    TextView user_name;
    CircleImageView user_profile_img;
    BackgroundMusic backgroundMusic;
    LinearLayout share_histry_linear;

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
        setContentView(R.layout.activity_history);


        Paper.init(this);
        init();
        backgroundMusic = BackgroundMusic.getInstance(this);
        final Date timestart = Calendar.getInstance().getTime();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        String user_name2 = Paper.book().read("user_name", "");
        if (!user_name2.equalsIgnoreCase("")) {
            user_name.setText(user_name2);
        }
        byte[] byteArray = Paper.book().read("profile", null);
        if (byteArray != null) {
            Bitmap bitmap_profile = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            if (bitmap_profile != null) {
                user_profile_img.setImageBitmap(bitmap_profile);
            }
        }


        best_time.setText("00:" + Paper.book().read("last_min", "00") + ":" + Paper.book().read("last_sec", "00"));



        high_score_arcade_data = Paper.book().read("high_score_arcade", "0");
        high_score_time_data = Paper.book().read("high_score_time", "0");



        int badge = Paper.book().read("daily_badges", 0);
        daily_badges.setText(String.valueOf(badge));


        historyModelArrayList = new ArrayList<>();
        categoryList = Paper.book().read("categoryList", null);
        if (categoryList == null) {
//            for (int i = 0; i < 24; i++) {



            historyModelArrayList.add(new HistoryModel(0, "0", "Arts"));
            historyModelArrayList.add(new HistoryModel(1, "0", "Board Games"));
            historyModelArrayList.add(new HistoryModel(2, "0", "Film"));
            historyModelArrayList.add(new HistoryModel(3, "0", "Animals"));
            historyModelArrayList.add(new HistoryModel(4, "0", "Books"));
            historyModelArrayList.add(new HistoryModel(5, "0", "Music"));
            historyModelArrayList.add(new HistoryModel(6, "0", "General Knowledge"));
            historyModelArrayList.add(new HistoryModel(7, "0", "History"));
            historyModelArrayList.add(new HistoryModel(8, "0", "Comics"));
            historyModelArrayList.add(new HistoryModel(9, "0", "Geography"));


//            }
        } else {
            for (int i = 0; i < 10; i++) {

                String cate_percentage = "cate_percentage" + i;
                String cat_percnt = Paper.book().read(cate_percentage, "0");

                total_cat_percentage = total_cat_percentage + Integer.parseInt(cat_percnt);

                historyModelArrayList.add(new HistoryModel(i, cat_percnt, categoryList.get(i).getCat_name()));
            }
        }


        int arcade = ((Integer.parseInt(high_score_arcade_data) * 33) / 1000000);
        int time = ((Integer.parseInt(high_score_time_data) * 33) / 1000000);
        int cate = ((total_cat_percentage * 34) / 2400);


        int total_percntage = arcade + time + cate;

        int setdata = ((total_percntage * 100) / 100);
        game_complt.setText(setdata + "%");

        historyAdapter = new HistoryAdapter(HistoryActivity.this, historyModelArrayList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3, RecyclerView.VERTICAL, false);
        cate_histry_RV.setLayoutManager(layoutManager);
        cate_histry_RV.setAdapter(historyAdapter);


        share_histy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(share_histry_linear);
            }
        });

    }

    private void init() {


        high_score_time = findViewById(R.id.high_score_time);
        backbtn = findViewById(R.id.backbtn);
        best_time = findViewById(R.id.best_time);
        user_name = findViewById(R.id.user_name);
        cate_histry_RV = findViewById(R.id.cate_histry_RV);
        user_profile_img = findViewById(R.id.user_profile_img);
        game_complt = findViewById(R.id.game_complt);
        daily_badges = findViewById(R.id.daily_badges);
        share_histry_linear = findViewById(R.id.share_histry_linear);
        share_histy = findViewById(R.id.share_histy);
    }

    private void shareImage(LinearLayout certificate_img) {
        Bitmap bitmap = getBitmapFromView(certificate_img);

        try {
            File file = new File(this.getExternalCacheDir(), "Quiz App.jpg");
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();

            file.setReadable(true, false);

            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.setType("image/jpg");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(intent, "Share image via"), 2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("errorShare" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return returnedBitmap;
    }


}