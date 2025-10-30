package com.example.recipeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.Model.Entity.RecipeWithUser;
import com.example.recipeapp.Model.Repository.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;
    private LiveData<List<Recipe>> allRecipe;
    private LiveData<List<RecipeWithUser>> allRecipeWithUser;
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeRepository  = new RecipeRepository(application);
        allRecipe = recipeRepository.getAllRecipe();
        allRecipeWithUser = recipeRepository.getAllRecipeWithUser();
    }
    public void insertRecipe(Recipe recipe){
        recipeRepository.insertRecipe(recipe);
    }
    public void updateRecipe(Recipe recipe){
        recipeRepository.updateRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        recipeRepository.deleteRecipe(recipe);
    }

    public LiveData<List<Recipe>> getAllRecipe(){
        return allRecipe;
    }

    public LiveData<List<RecipeWithUser>> getAllRecipeWithUser(){
        return allRecipeWithUser;
    }
    public LiveData<List<RatedRecipe>> getRecipeReviewStats(){
        return recipeRepository.getRecipeReviewStats();
    }
    public LiveData<List<TopRecipe>> getTopRecipe(){
        return recipeRepository.getTopRecipe();
    }
    public LiveData<RatedRecipe> getTopRecipeDetailByID(int recipeID){
        return recipeRepository.getTopRecipeDetailByID(recipeID);
    }
    public LiveData<List<RatedRecipe>> getAllTopRecipeDetail(){
        return recipeRepository.getAllTopRecipeDetail();
    }
    public LiveData<List<RatedRecipe>> getRatedRecipeByUserID(int userID){
        return recipeRepository.getRatedRecipeByUserID(userID);
    }
    public LiveData<List<RatedRecipe>> getListFavoriteRecipeByUserID(int userID,boolean like){
        return recipeRepository.getListFavoriteRecipeByUserID(userID,like);
    }
}
