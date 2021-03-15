package com.renesis.tech.androidassignment.quizgame.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryListModel;
import com.renesis.tech.androidassignment.quizgame.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    ArrayList<Object> categoryListModels;
    Context context;
    Callback callback;

    Activity activity;





    public CategoryListAdapter(Activity activity, ArrayList<Object> categoryListModels, Context context, Callback callback) {
        this.categoryListModels = categoryListModels;
        this.context = context;
        this.callback = callback;
        this.activity = activity;
        Paper.init(activity);

    }



    @Override
    public CategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.category_list_item, parent, false);
        return new CategoryListAdapter.MyViewHolder(view);

    }



    @Override
    public void onBindViewHolder(final CategoryListAdapter.MyViewHolder holder, final int position) {


        CategoryListModel menuItem = (CategoryListModel) categoryListModels.get(position);




        holder.cat_name.setText(menuItem.getCat_name());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.montserrat_semibold);
        holder.cat_name.setTypeface(typeface);


            holder.cat_icon.setImageResource(menuItem.getCat_icon());



        holder.initClickListener(menuItem);

    }






    @Override
    public int getItemCount() {

        return categoryListModels.size();

    }

    public interface Callback {
        //        void onItemClick(int pos, String cat_name, int total_question, int id,int colorcode);
        void onItemClick(int pos, CategoryListModel categoryListModel);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cat_name;
        ImageView cat_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.cat_name);
            cat_icon = itemView.findViewById(R.id.cat_icon);
        }

        private void initClickListener(final CategoryListModel menuItem) {
            cat_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onItemClick(getAdapterPosition(), menuItem);

                }
            });
        }


    }




}
