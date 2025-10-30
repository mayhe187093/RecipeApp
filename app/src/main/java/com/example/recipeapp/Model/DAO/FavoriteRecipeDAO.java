package com.example.recipeapp.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeapp.Model.Entity.FavoriteRecipe;

import java.util.List;

@Dao
public interface FavoriteRecipeDAO {
    @Insert
    void insertFavoriteRecipe(FavoriteRecipe favoriteRecipe);
    @Update
    void updateStatusFavorite(FavoriteRecipe favoriteRecipe);
    @Delete
    void deleteFavoriteRecipe(FavoriteRecipe favoriteRecipe);
    @Query("select * from FavoriteRecipe where userID = :userID")
    LiveData<List<FavoriteRecipe>> getListFavoriteRecipeByUserID(int userID);
}
