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
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_top_3_recipe, parent, false);
        return new Top3RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top3RecipeViewHolder holder, int position) {
        TopRecipe topRecipe = listTopRecipe.get(position);
        if(topRecipe == null){
            return;
        }
        Glide.with(activity).load(topRecipe.getImgPath()).into(holder.top3RecipeImg);
        holder.itemView.setOnClickListener(v -> {
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

        public Top3RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            top3RecipeImg = itemView.findViewById(R.id.top3RecipeImage);
        }
    }

}
