package com.example.recipeapp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.databinding.LayoutCookedRecipesBinding;

import java.util.ArrayList;

public class AdapterCookedRecipe extends RecyclerView.Adapter<AdapterCookedRecipe.CookedRecipeViewHolder> {

    private ArrayList<RatedRecipe> list;
    private Fragment fragment;
    private RecipeViewModel recipeViewModel;
    private User user;

    public AdapterCookedRecipe(ArrayList<RatedRecipe> list, Fragment fragment, RecipeViewModel recipeViewModel) {
        this.list = list;
        this.fragment = fragment;
        this.recipeViewModel = recipeViewModel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public interface setOnItemClickListenerAdapter {
        void onItemClick(RatedRecipe ratedRecipe);
    }

    private setOnItemClickListenerAdapter listener;

    public void setOnItemClickListenerAdapter(setOnItemClickListenerAdapter listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CookedRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutCookedRecipesBinding binding = LayoutCookedRecipesBinding.inflate(inflater, parent, false);
        return new CookedRecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CookedRecipeViewHolder holder, int position) {
        RatedRecipe recipe = list.get(position);
        if (recipe == null) {
            return;
        }
        int totalAvg = 0;
        int totalComment = recipe.listReview.size();
        for (Review review : recipe.listReview) {
            totalAvg += review.getRating();
        }
        double avg = (double) totalAvg / totalComment > 0 ? totalAvg / totalComment : 0;
        holder.binding.nameCookedRecipes.setText(recipe.recipe.getRecipeName());
        holder.binding.avgRatingCookedRecipe.setText(avg != 0 ? String.format("%.1f ★", avg) : "Chưa có đánh giá");
        holder.binding.numberCommentCookedRecipe.setText(totalComment != 0 ? String.valueOf(totalComment) + " Review" : "");
        Glide.with(fragment).load(recipe.recipe.getImgPath()).into(holder.binding.imgCookedRecipe);
        holder.binding.deleteCookedRecipe.setOnClickListener(v -> {
            AlertDialog.Builder confirm = new AlertDialog.Builder(fragment.getContext());
            confirm.setTitle("Bạn có chắc chắn xóa món ăn này");
            confirm.setPositiveButton("Xóa", (dialog, which) -> {
                recipeViewModel.deleteRecipe(recipe.recipe);
                dialog.dismiss();
            });
            confirm.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            confirm.show();
        });
        if (user!=null) {
            if (user.getUserID() != recipe.user.getUserID()) {
                holder.binding.deleteCookedRecipe.setVisibility(View.GONE);
            }
        }
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class CookedRecipeViewHolder extends RecyclerView.ViewHolder {
        LayoutCookedRecipesBinding binding;

        public CookedRecipeViewHolder(@NonNull LayoutCookedRecipesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
