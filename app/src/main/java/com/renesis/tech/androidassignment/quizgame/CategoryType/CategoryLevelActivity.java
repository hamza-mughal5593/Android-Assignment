package com.renesis.tech.androidassignment.quizgame.CategoryType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.renesis.tech.androidassignment.quizgame.Adapter.CategoryLevelAdapter;
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryLevelModel;
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryListModel;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CategoryLevelActivity extends AppCompatActivity implements CategoryLevelAdapter.Callback {
    TextView cat_name;
    CategoryListModel categoryListModel;
    CategoryLevelAdapter categoryLevelAdapter;
    RecyclerView recycler;
    ImageView backbtn;
    ArrayList<CategoryLevelModel> categoryLevelModelArrayList;
    public static TextView cat_percent;



    BackgroundMusic backgroundMusic;
    @Override
    protected void onPause() {
        backgroundMusic.pausemusic();
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_level);
        Paper.init(this);

        backgroundMusic = BackgroundMusic.getInstance(this);



        init();

        categoryListModel = Paper.book().read("categoryListModel", null);
        cat_name.setText(categoryListModel.getCat_name());


        int total_level = categoryListModel.getTotal_questions() / 20;

        categoryLevelModelArrayList = new ArrayList<>();
        for (int i = 1; i <= total_level; i++) {
            String checklevel = "lock_level_check" + i + Paper.book().read("categoryListModel_pos", null);
            categoryLevelModelArrayList.add(new CategoryLevelModel(i, categoryListModel.getCat_icon(), "true", Paper.book().read(checklevel, "true")));
        }


        categoryLevelAdapter = new CategoryLevelAdapter(CategoryLevelActivity.this, categoryLevelModelArrayList, getApplicationContext(), CategoryLevelActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(categoryLevelAdapter);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        cat_name = findViewById(R.id.cat_name);
        recycler = findViewById(R.id.recycler);
        backbtn = findViewById(R.id.backbtn);
        cat_percent = findViewById(R.id.cat_percent);
    }

    @Override
    public void onItemClick(int pos, CategoryLevelModel categoryLevelModel) {
        Paper.book().write("categoryLevelModel", categoryLevelModel);
        backgroundMusic.Stopmusic_background();
        Intent intent = new Intent(CategoryLevelActivity.this, CategoryQuestionActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onResume() {
        backgroundMusic.playmusic();

        if (categoryLevelAdapter != null) {
            categoryLevelAdapter.notifyDataSetChanged();
            CategoryLevelAdapter.level_counter = 1;
            CategoryLevelAdapter.total_percentage = 0;
            Paper.init(this);
        }
        super.onResume();
    }
}