package com.example.recipeapp.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.DAO.FavoriteRecipeDAO;
import com.example.recipeapp.Model.Entity.FavoriteRecipe;

import java.util.List;

public class FavoriteRecipeRepository {
    private FavoriteRecipeDAO favoriteRecipeDAO;
    public FavoriteRecipeRepository(Application application){
        DBConnection db = DBConnection.getINSTANCE(application);
        favoriteRecipeDAO = db.createFavoriteRecipeDAO();
    }
    public void insertFavoriteRecipe(FavoriteRecipe favoriteRecipe){
        DBConnection.databaseWriteExecutor.execute(() -> favoriteRecipeDAO.insertFavoriteRecipe(favoriteRecipe));
    }

    public void updateStatusFavorite(FavoriteRecipe favoriteRecipe){
        DBConnection.databaseWriteExecutor.execute(() -> favoriteRecipeDAO.updateStatusFavorite(favoriteRecipe));
    }

    public void deleteFavoriteRecipe(FavoriteRecipe favoriteRecipe){
        DBConnection.databaseWriteExecutor.execute(() -> favoriteRecipeDAO.deleteFavoriteRecipe(favoriteRecipe));
    }

    public LiveData<List<FavoriteRecipe>> getListFavoriteRecipeByUserID(int userID){
        return favoriteRecipeDAO.getListFavoriteRecipeByUserID(userID);
    }
}
