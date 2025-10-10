package com.example.recipeapp.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.Model.Entity.TopRecipeDetail;
import com.example.recipeapp.Model.Entity.RecipeWithUser;

import java.util.List;
@Dao
public interface RecipeDAO {
    @Insert
    void insertRecipe(Recipe recipe);
    @Update
    void updateRecipe(Recipe recipe);
    @Delete
    void deleteRecipe(Recipe recipe);
    @Query("select * from Recipe")
    LiveData<List<Recipe>> getAllRecipe();
    @Transaction
    @Query("SELECT * FROM Recipe")
    LiveData<List<RecipeWithUser>> getAllRecipeWithUser();
    @Transaction
    @Query("select * from Recipe")
    LiveData<List<TopRecipeDetail>> getRecipeReviewStats();

    @Query( "select avg(r.rating) as avgRating,count(r.recipeID) as numberOfReviews,r.recipeID,re.recipeName,u.fullName,c.categoryName,re.ingredient,re.description,re.imgPath\n" +
            " from Review r inner join Recipe re on r.recipeID = re.recipeID inner join User u on u.userID = re.userID inner join Category c on c.categoryID = re.categoryID \n" +
            " group by r.recipeID \n" +
            " Having avg(r.rating) >=4\n" +
            " order by avg(r.rating) desc" )
    LiveData<List<TopRecipe>> getTopRecipe();

    @Transaction
    @Query("select * from Recipe where recipeID = :recipeID")
    LiveData<TopRecipeDetail> getTopRecipeDetailByID(int recipeID);
    @Transaction
    @Query("select * from Recipe")
    LiveData<List<TopRecipeDetail>> getAllTopRecipeDetail();
}
