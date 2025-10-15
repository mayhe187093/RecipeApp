package com.example.recipeapp.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.databinding.LayoutTopRecipeBinding;

import java.io.File;
import java.util.ArrayList;

public class AdapterTopRecipe extends RecyclerView.Adapter<AdapterTopRecipe.TopRecipeViewHolder>{
    private ArrayList<TopRecipe> list;
    private Activity activity;

    public AdapterTopRecipe(Activity activity, ArrayList<TopRecipe> list) {
        this.activity = activity;
        this.list = list;
    }

    public interface setOnItemOnClickAdapter{
        void onClickItem(TopRecipe topRecipe);
    }
    private setOnItemOnClickAdapter listener;
    public void setOnItemListener(setOnItemOnClickAdapter listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public TopRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutTopRecipeBinding binding = LayoutTopRecipeBinding.inflate(inflater,parent,false);
        return new TopRecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecipeViewHolder holder, int position) {
        TopRecipe topRecipe = list.get(position);
        holder.layoutTopRecipeBinding.nameTopItemRecipe.setText(topRecipe.getRecipeName());
        holder.layoutTopRecipeBinding.nameTopItemUser.setText(topRecipe.getFullName());
        holder.layoutTopRecipeBinding.avgRating.setText("Rating: "+topRecipe.getAvgRating()+"★");
        holder.layoutTopRecipeBinding.numberComment.setText("Comments: "+topRecipe.getNumberOfReviews());
        Glide.with(activity).load(topRecipe.getImgPath()).into(holder.layoutTopRecipeBinding.imgTopItemRecipe);
        holder.layoutTopRecipeBinding.getRoot().setOnClickListener(v -> {
            if(listener!=null){
                listener.onClickItem(topRecipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class TopRecipeViewHolder extends RecyclerView.ViewHolder {
        private LayoutTopRecipeBinding layoutTopRecipeBinding;
        private TextView nameTopItemRecipe;
        private TextView nameTopItemUser;
        private TextView avgRating;
        private TextView numberComment;
        private ImageView imgTopItemRecipe;
        public TopRecipeViewHolder(@NonNull LayoutTopRecipeBinding layoutTopRecipeBinding) {
            super(layoutTopRecipeBinding.getRoot());
            this.layoutTopRecipeBinding = layoutTopRecipeBinding;
            this.nameTopItemRecipe = layoutTopRecipeBinding.nameTopItemRecipe;
            this.nameTopItemUser = layoutTopRecipeBinding.nameTopItemUser;
            this.avgRating = layoutTopRecipeBinding.avgRating;
            this.numberComment = layoutTopRecipeBinding.numberComment;
            this.imgTopItemRecipe = layoutTopRecipeBinding.imgTopItemRecipe;
        }
    }

}
