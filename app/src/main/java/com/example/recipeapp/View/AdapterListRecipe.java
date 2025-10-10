package com.example.recipeapp.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.RecipeWithUser;
import com.example.recipeapp.Model.Entity.TopRecipeDetail;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.LayoutSearchBinding;

import java.io.File;
import java.util.ArrayList;
public class AdapterListRecipe extends ArrayAdapter<TopRecipeDetail> {
    private Activity context;
    private ArrayList<TopRecipeDetail> listRecipeWithUser;
    public AdapterListRecipe(@NonNull Activity context, int IDLayout, ArrayList<TopRecipeDetail> listRecipeWithUser) {
        super(context, 0, listRecipeWithUser);
        this.context = context;
        this.listRecipeWithUser = listRecipeWithUser;
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
        TopRecipeDetail topRecipeDetail = listRecipeWithUser.get(position);
        Recipe recipe = topRecipeDetail.recipe;
        User user = topRecipeDetail.user;
        binding.nameItemRecipe.setText(recipe.getRecipeName());
        binding.nameItemUser.setText(user != null ?user.getFullName():"Guest");

        File imgFile = new File(recipe.getImgPath());
        Glide.with(context)
                .load(imgFile)
                .into(binding.imgItemRecipe);
        return convertView;
    }
}
