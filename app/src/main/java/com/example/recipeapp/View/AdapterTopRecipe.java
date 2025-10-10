package com.example.recipeapp.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.Category;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.Model.Entity.TopRecipeDetail;
import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.databinding.LayoutTopRecipeBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterTopRecipe extends ArrayAdapter<TopRecipe> {
    private Activity context;
    private ArrayList<TopRecipe> list;
    public AdapterTopRecipe(@NonNull Activity context,
                            @NonNull ArrayList<TopRecipe> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        LayoutTopRecipeBinding binding;

        if(convertView == null){
            binding = LayoutTopRecipeBinding.inflate(inflater,parent,false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (LayoutTopRecipeBinding) convertView.getTag();
        }
        TopRecipe topRecipe = list.get(position);
        binding.nameTopItemRecipe.setText(topRecipe.getRecipeName());
        binding.nameTopItemUser.setText(topRecipe.getFullName());
        binding.avgRating.setText(String.format("%.2f★", topRecipe.getAvgRating()));
        binding.numberComment.setText(String.valueOf(topRecipe.getNumberOfReviews())+" Review");

        File img = new File(topRecipe.getImgPath());
        Glide.with(context).
                load(img).
                into(binding.imgTopItemRecipe);
        return convertView;
    }
}
