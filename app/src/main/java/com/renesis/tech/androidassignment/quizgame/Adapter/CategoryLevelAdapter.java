package com.renesis.tech.androidassignment.quizgame.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.renesis.tech.androidassignment.quizgame.CategoryType.CategoryLevelActivity;
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryLevelModel;
import com.renesis.tech.androidassignment.quizgame.R;

import java.util.ArrayList;

import io.paperdb.Paper;


public class CategoryLevelAdapter extends RecyclerView.Adapter<CategoryLevelAdapter.MyViewHolder> {
    ArrayList<CategoryLevelModel> categoryLevelModelArrayList;
    Context context;
    CategoryLevelAdapter.Callback callback;

    Activity activity;
    public static int level_counter = 1;

    public static int total_percentage = 0;

    public CategoryLevelAdapter(Activity activity, ArrayList<CategoryLevelModel> categoryLevelModelArrayList, Context context, CategoryLevelAdapter.Callback callback) {
        this.categoryLevelModelArrayList = categoryLevelModelArrayList;
        this.context = context;
        this.callback = callback;
        this.activity = activity;


    }

    @Override
    public CategoryLevelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.category_level_item, parent, false);
        return new CategoryLevelAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CategoryLevelAdapter.MyViewHolder holder, final int position) {
        Paper.init(activity);
        int level = categoryLevelModelArrayList.get(position).getId();
        level = level++;
        holder.level_text.setText("Level " + level);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.montserrat_semibold);
        holder.level_text.setTypeface(typeface);
        holder.level_question_text.setTypeface(typeface);
        holder.btn.setTypeface(typeface);
        holder.cat_icon.setImageResource(categoryLevelModelArrayList.get(position).getCat_icon());


        String totalcorrect_ans = "total_correct_ans" + categoryLevelModelArrayList.get(position).getId() + String.valueOf(Paper.book().read("categoryListModel_pos", "0"));
        int correct_ans = Integer.parseInt(Paper.book().read(totalcorrect_ans, "0"));

//        correct_ans = Utilities.getInt(context,totalcorrect_ans);

        if (correct_ans != 0)
            correct_ans = correct_ans - 1;


        total_percentage = total_percentage + correct_ans;


        int total_value = categoryLevelModelArrayList.size() * 20;
        String finalRWt = ((total_percentage * 100) / total_value) + "";
        CategoryLevelActivity.cat_percent.setText(finalRWt + "%");

        String cate_percentage = "cate_percentage" + Paper.book().read("categoryListModel_pos", 0);
        Paper.book().write(cate_percentage, finalRWt);


        holder.level_question_text.setText("Question " + String.valueOf(correct_ans) + "/20");
//        holder.level_question_text.setText("Question " + total_question_asked + "/20");
        if (correct_ans == 20) {

            level_counter = level_counter + 1;


            if (level_counter < categoryLevelModelArrayList.size()) {
                categoryLevelModelArrayList.get(level_counter).setCheck_level("false");
                String check_level = "lock_level_check" + categoryLevelModelArrayList.get(level_counter).getId() + Paper.book().read("categoryListModel_pos", null);
                Paper.book().write(check_level, "false");
            }


            holder.lock_level.setImageResource(R.drawable.tick_1);
            holder.lock_level.setVisibility(View.VISIBLE);
            holder.btn.setVisibility(View.GONE);
            holder.btn.setText("Replay");
        } else if (correct_ans < 20 && correct_ans != 0) {
            holder.btn.setText("Continue");
        } else if (correct_ans == 0) {
            holder.btn.setText("Play");

        }



            String checkad = "checkad" + categoryLevelModelArrayList.get(position).getId() + Paper.book().read("categoryListModel_pos", null);
            Paper.book().write(checkad, "false");
            categoryLevelModelArrayList.get(position).setCheck_ad("false");
            holder.btn.setVisibility(View.VISIBLE);
            holder.lock_level.setVisibility(View.GONE);
            holder.level_question_text.setVisibility(View.VISIBLE);
            holder.main_border.setAlpha(1f);
            categoryLevelModelArrayList.get(position).setCheck_level("false");
            String check_level = "lock_level_check" + categoryLevelModelArrayList.get(position).getId() + Paper.book().read("categoryListModel_pos", null);
            Paper.book().write(check_level, "false");


            categoryLevelModelArrayList.get(position).setCheck_level("false");
            Paper.book().write(check_level, "false");







        holder.initClickListener();
    }

    @Override
    public int getItemCount() {
        return categoryLevelModelArrayList.size();
    }

    public interface Callback {
        void onItemClick(int pos, CategoryLevelModel categoryLevelModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView level_text, level_question_text, btn;
        ImageView cat_icon, lock_level;
        RelativeLayout main_border;

        public MyViewHolder(View itemView) {
            super(itemView);
            level_text = itemView.findViewById(R.id.level_text);
            level_question_text = itemView.findViewById(R.id.level_question_text);
            btn = itemView.findViewById(R.id.btn);
            cat_icon = itemView.findViewById(R.id.cat_icon);
            main_border = itemView.findViewById(R.id.main_border);
            lock_level = itemView.findViewById(R.id.lock_level);
        }

        private void initClickListener() {
            main_border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!btn.getText().toString().equalsIgnoreCase("Replay")) {

                        if (categoryLevelModelArrayList.get(getAdapterPosition()).getCheck_level().equalsIgnoreCase("false")) {


                            callback.onItemClick(getAdapterPosition(), categoryLevelModelArrayList.get(getAdapterPosition()));

                        } else {
                            Toast.makeText(activity, "LOCKED", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Level Already Completed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


}
