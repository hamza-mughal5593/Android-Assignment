package com.renesis.tech.androidassignment.quizgame.CategoryType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.renesis.tech.androidassignment.quizgame.Adapter.CategoryListAdapter;
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryListModel;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CategoryListActivity extends AppCompatActivity implements CategoryListAdapter.Callback {
    CategoryListAdapter categoryListAdapter;
    RecyclerView recyclerView;
    ImageView backbtn;
    ArrayList<Object> categoryList;
    ArrayList<CategoryListModel> categoryList_model;
    RelativeLayout loader_main;



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
        setContentView(R.layout.activity_category_list);
        Paper.init(this);
        init();

        backgroundMusic = BackgroundMusic.getInstance(this);
        categoryList = new ArrayList<>();






        categoryList.add(new CategoryListModel(R.color.color1, Paper.book().read("islocked1", "false"), R.drawable.arts, R.drawable.arts_lock, "Arts", 20, 25));
        categoryList.add(new CategoryListModel(R.color.color2, Paper.book().read("islocked2", "false"), R.drawable.board_games, R.drawable.board_game_lock, "Board Games", 40, 16));
        categoryList.add(new CategoryListModel(R.color.color3, Paper.book().read("islocked3", "false"), R.drawable.film, R.drawable.film_lock, "Film", 200, 11));
        categoryList.add(new CategoryListModel(R.color.color4, Paper.book().read("islocked4", "true"), R.drawable.animals, R.drawable.animals_lock, "Animals", 60, 27));
        categoryList.add(new CategoryListModel(R.color.color5, Paper.book().read("islocked5", "true"), R.drawable.books, R.drawable.books_lock, "Books", 80, 10));
        categoryList.add(new CategoryListModel(R.color.color6, Paper.book().read("islocked6", "true"), R.drawable.music, R.drawable.music_lock, "Music", 320, 12));
        categoryList.add(new CategoryListModel(R.color.color7, Paper.book().read("islocked8", "true"), R.drawable.general_knowledge, R.drawable.general_knowledge_lock, "General Knowledge", 260, 9));
        categoryList.add(new CategoryListModel(R.color.color8, Paper.book().read("islocked9", "true"), R.drawable.hist, R.drawable.history_lock, "History", 260, 23));
        categoryList.add(new CategoryListModel(R.color.color9, Paper.book().read("islocked10", "true"), R.drawable.comics, R.drawable.comics_lock, "Comics", 40, 29));
        categoryList.add(new CategoryListModel(R.color.color10, Paper.book().read("islocked11", "true"), R.drawable.geography, R.drawable.geography_lock, "Geography", 240, 22));

        categoryList_model = new ArrayList<>();

        categoryList_model.add(new CategoryListModel(R.color.color1, Paper.book().read("islocked0", "false"), R.drawable.arts, R.drawable.arts_lock, "Arts", 20, 25));
        categoryList_model.add(new CategoryListModel(R.color.color2, Paper.book().read("islocked1", "false"), R.drawable.board_games, R.drawable.board_game_lock, "Board Games", 40, 16));
        categoryList_model.add(new CategoryListModel(R.color.color3, Paper.book().read("islocked2", "false"), R.drawable.film, R.drawable.film_lock, "Film", 200, 11));
        categoryList_model.add(new CategoryListModel(R.color.color4, Paper.book().read("islocked3", "true"), R.drawable.animals, R.drawable.animals_lock, "Animals", 60, 27));
        categoryList_model.add(new CategoryListModel(R.color.color5, Paper.book().read("islocked4", "true"), R.drawable.books, R.drawable.books_lock, "Books", 80, 10));
        categoryList_model.add(new CategoryListModel(R.color.color6, Paper.book().read("islocked5", "true"), R.drawable.music, R.drawable.music_lock, "Music", 320, 12));
        categoryList_model.add(new CategoryListModel(R.color.color7, Paper.book().read("islocked6", "true"), R.drawable.general_knowledge, R.drawable.general_knowledge_lock, "General Knowledge", 260, 9));
        categoryList_model.add(new CategoryListModel(R.color.color8, Paper.book().read("islocked7", "true"), R.drawable.hist, R.drawable.history_lock, "History", 260, 23));
        categoryList_model.add(new CategoryListModel(R.color.color9, Paper.book().read("islocked8", "true"), R.drawable.comics, R.drawable.comics_lock, "Comics", 40, 29));
        categoryList_model.add(new CategoryListModel(R.color.color10, Paper.book().read("islocked9", "true"), R.drawable.geography, R.drawable.geography_lock, "Geography", 240, 22));



            for (int i=0; i<24;i++){
                Paper.book().write("islocked"+i,"false");
            }
            loadMenu();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler);
        backbtn = findViewById(R.id.backbtn);
        loader_main = findViewById(R.id.loader_main);
    }



    private void loadMenu() {

        Paper.book().write("categoryList", categoryList_model);
        loader_main.setVisibility(View.GONE);
        categoryListAdapter = new CategoryListAdapter(CategoryListActivity.this, categoryList, getApplicationContext(), CategoryListActivity.this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryListAdapter);
    }


    @Override
    public void onItemClick(int pos, CategoryListModel categoryListModel) {

        Paper.book().write("categoryListModel", categoryListModel);
        Paper.book().write("categoryListModel_pos", pos);
        Intent intent = new Intent(CategoryListActivity.this, CategoryLevelActivity.class);
        startActivity(intent);


    }
}