package com.example.recipeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.Entity.FavoriteRecipe;
import com.example.recipeapp.Model.Repository.FavoriteRecipeRepository;

import java.util.List;

public class FavoriteRecipeViewModel extends AndroidViewModel {
    private FavoriteRecipeRepository favoriteRecipeRepository;

    public FavoriteRecipeViewModel(@NonNull Application application) {
        super(application);
        favoriteRecipeRepository = new FavoriteRecipeRepository(application);
    }

    public void insertFavoriteRecipe(FavoriteRecipe favoriteRecipe){
        favoriteRecipeRepository.insertFavoriteRecipe(favoriteRecipe);
    }

    public void updateStatusFavorite(FavoriteRecipe favoriteRecipe){
        favoriteRecipeRepository.updateStatusFavorite(favoriteRecipe);
    }

    public void deleteFavoriteRecipe(FavoriteRecipe favoriteRecipe){
        favoriteRecipeRepository.deleteFavoriteRecipe(favoriteRecipe);
    }

    public LiveData<List<FavoriteRecipe>> getListFavoriteRecipeByUserID(int userID){
        return favoriteRecipeRepository.getListFavoriteRecipeByUserID(userID);
    }
}
