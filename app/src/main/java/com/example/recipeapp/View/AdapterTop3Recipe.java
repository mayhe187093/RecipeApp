package com.example.recipeapp.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.LayoutTop3RecipeBinding;

import java.util.ArrayList;

public class AdapterTop3Recipe extends RecyclerView.Adapter<AdapterTop3Recipe.Top3RecipeViewHolder> {

    private ArrayList<TopRecipe> listTopRecipe;
    private Activity activity;
    public interface setOnItemClickListenerAdapter{
        void onItemClick(TopRecipe topRecipe);
    }
    private setOnItemClickListenerAdapter listener;
    public void setOnItemListener(setOnItemClickListenerAdapter listener){
        this.listener = listener;
    }
    public AdapterTop3Recipe(Activity activity, ArrayList<TopRecipe> listTopRecipe) {
        this.activity = activity;
        this.listTopRecipe = listTopRecipe;
    }

    @NonNull
    @Override
    public Top3RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutTop3RecipeBinding binding = LayoutTop3RecipeBinding.inflate(inflater,parent,false);
        return new Top3RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Top3RecipeViewHolder holder, int position) {
        TopRecipe topRecipe = listTopRecipe.get(position);
        if(topRecipe == null){
            return;
        }
        Glide.with(activity).load(topRecipe.getImgPath()).into(holder.binding.top3RecipeImage);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(topRecipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listTopRecipe != null) {
            return listTopRecipe.size();
        }
        return 0;
    }

    class Top3RecipeViewHolder extends RecyclerView.ViewHolder {
        private ImageView top3RecipeImg;
        private LayoutTop3RecipeBinding binding;
        public Top3RecipeViewHolder(@NonNull LayoutTop3RecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            top3RecipeImg = binding.top3RecipeImage;
        }
    }

}
