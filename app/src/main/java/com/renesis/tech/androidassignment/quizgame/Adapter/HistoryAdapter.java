package com.renesis.tech.androidassignment.quizgame.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryListModel;
import com.renesis.tech.androidassignment.quizgame.Pojo.HistoryModel;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.Utils.ProgressBarAnimation;

import java.util.ArrayList;

import io.paperdb.Paper;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    ArrayList<HistoryModel> historyModelArrayList;
    Context context;
    ProgressBarAnimation animation_progress_bar;
    ArrayList<CategoryListModel> categoryList;
    Activity activity;

    public HistoryAdapter(Activity activity, ArrayList<HistoryModel> historyModelArrayList, Context context) {
        this.historyModelArrayList = historyModelArrayList;
        this.context = context;

        this.activity = activity;

    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.MyViewHolder holder, final int position) {

        holder.tv.setText(historyModelArrayList.get(position).getTotal_percentage());
        holder.cate_name.setText(historyModelArrayList.get(position).getCate_name());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.montserrat_semibold);
        holder.tv.setTypeface(typeface);
        holder.cate_name.setTypeface(typeface);


        categoryList = Paper.book().read("categoryList",null);

        if (categoryList!=null){

            LayerDrawable progressBarDrawable = (LayerDrawable)  holder.circularProgressbar.getProgressDrawable();
            Drawable backgroundDrawable = progressBarDrawable.getDrawable(0);
            backgroundDrawable.setColorFilter(ContextCompat.getColor(activity, categoryList.get(position).getCat_color()), PorterDuff.Mode.SRC_IN);
        }

        animation_progress_bar = new ProgressBarAnimation(activity, holder.circularProgressbar, holder.tv, 0f, Float.parseFloat(historyModelArrayList.get(position).getTotal_percentage()));
        animation_progress_bar.setDuration(4000);
        holder.circularProgressbar.setAnimation(animation_progress_bar);

    }

    @Override
    public int getItemCount() {

        return historyModelArrayList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv,cate_name;
        ProgressBar circularProgressbar;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            cate_name = itemView.findViewById(R.id.cate_name);
            circularProgressbar = itemView.findViewById(R.id.circularProgressbar);
        }

    }


}
