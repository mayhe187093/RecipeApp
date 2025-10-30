package com.example.recipeapp.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.DAO.RecipeDAO;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.Model.Entity.RecipeWithUser;

import java.util.List;

public class RecipeRepository {
    private RecipeDAO recipeDAO;
    private LiveData<List<Recipe>> allRecipe;
    private LiveData<List<RecipeWithUser>> allRecipeWithUser;
    public RecipeRepository(Application application){
        DBConnection db = DBConnection.getINSTANCE(application);
        recipeDAO = db.createRecipeDao();
        allRecipeWithUser = recipeDAO.getAllRecipeWithUser();
        allRecipe = recipeDAO.getAllRecipe();
    }
    public void insertRecipe(Recipe recipe){
        DBConnection.databaseWriteExecutor.execute(() -> recipeDAO.insertRecipe(recipe));
    }
    public void updateRecipe(Recipe recipe){
        DBConnection.databaseWriteExecutor.execute(() -> recipeDAO.updateRecipe(recipe));
    }
    public void deleteRecipe(Recipe recipe){
        DBConnection.databaseWriteExecutor.execute(() -> recipeDAO.deleteRecipe(recipe));
    }

    public LiveData<List<Recipe>> getAllRecipe(){
        return allRecipe;
    }
    public LiveData<List<RecipeWithUser>> getAllRecipeWithUser(){
        return allRecipeWithUser;
    }

    public LiveData<List<RatedRecipe>> getRecipeReviewStats(){
        return recipeDAO.getRecipeReviewStats();
    }
    public LiveData<List<TopRecipe>> getTopRecipe(){
        return recipeDAO.getTopRecipe();
    }
    public LiveData<RatedRecipe> getTopRecipeDetailByID(int recipeID){
        return recipeDAO.getTopRecipeDetailByID(recipeID);
    }
    public LiveData<List<RatedRecipe>> getAllTopRecipeDetail(){
        return recipeDAO.getAllTopRecipeDetail();
    }
    public LiveData<List<RatedRecipe>> getRatedRecipeByUserID(int userID){
        return recipeDAO.getRatedRecipeByUserID(userID);
    }
    public LiveData<List<RatedRecipe>> getListFavoriteRecipeByUserID(int userID,boolean like){
        return recipeDAO.getListFavoriteRecipeByUserID(userID,like);
    }
}
