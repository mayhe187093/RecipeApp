package com.example.recipeapp.View;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.databinding.LayoutCookedRecipesBinding;
import com.example.recipeapp.databinding.LayoutFavoriteRecipeBinding;

import java.util.List;

public class AdapterListFavoriteRecipe extends RecyclerView.Adapter<AdapterListFavoriteRecipe.FavoriteRecipeViewHolder>{

    private List<RatedRecipe> list;
    private Activity activity;
    private RecipeViewModel recipeViewModel;
    public interface setItemOnClickListenerAdapter {
        void onItemClick(RatedRecipe recipe);
    };
    private setItemOnClickListenerAdapter listener;
    public void setOnItemListener(setItemOnClickListenerAdapter listener){
        this.listener = listener;
    }

    public AdapterListFavoriteRecipe(List<RatedRecipe> list,  Activity activity, RecipeViewModel recipeViewModel) {
        this.list = list;
        this.activity = activity;
        this.recipeViewModel = recipeViewModel;
    }

    @NonNull
    @Override
    public FavoriteRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutFavoriteRecipeBinding binding = LayoutFavoriteRecipeBinding.inflate(inflater,parent,false);
        return new FavoriteRecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecipeViewHolder holder, int position) {
        RatedRecipe recipe = list.get(position);
        holder.binding.nameFavoriteRecipe.setText(recipe.recipe.getRecipeName());
        holder.binding.nameAuthorFavoriteRecipe.setText(recipe.user.getFullName());
        Glide.with(holder.binding.imgFavoriteRecipe.getContext()).
                load(recipe.recipe.getImgPath()).
                into(holder.binding.imgFavoriteRecipe);
        holder.binding.getRoot().setOnClickListener(v -> {
            if(listener!=null){
                listener.onItemClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<RatedRecipe> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    class FavoriteRecipeViewHolder extends RecyclerView.ViewHolder {
        LayoutFavoriteRecipeBinding binding;
        public FavoriteRecipeViewHolder(@NonNull LayoutFavoriteRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
