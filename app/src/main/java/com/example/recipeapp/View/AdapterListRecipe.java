package com.example.recipeapp.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.databinding.LayoutSearchBinding;

import java.io.File;
import java.util.ArrayList;
public class AdapterListRecipe extends ArrayAdapter<RatedRecipe> {
    private Activity context;
    private ArrayList<RatedRecipe> list;
    public AdapterListRecipe(@NonNull Activity context, int IDLayout, ArrayList<RatedRecipe> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutSearchBinding binding;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            binding = LayoutSearchBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (LayoutSearchBinding) convertView.getTag();
        }
        RatedRecipe ratedRecipe = list.get(position);
        Recipe recipe = ratedRecipe.recipe;
        User user = ratedRecipe.user;
        binding.nameItemRecipe.setText(recipe.getRecipeName());
        binding.nameItemUser.setText(user != null ?user.getFullName():"Guest");

        File imgFile = new File(recipe.getImgPath());
        Glide.with(binding.imgItemRecipe.getContext())
                .load(imgFile)
                .into(binding.imgItemRecipe);
        return convertView;
    }
}
