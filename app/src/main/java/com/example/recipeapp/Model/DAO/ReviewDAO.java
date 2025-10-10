package com.example.recipeapp.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.ReviewWithRecipeWithUser;

import java.util.List;
@Dao
public interface ReviewDAO {
    @Insert
    void insertReview(Review review);
    @Update
    void updateReview(Review review);
    @Delete
    void deleteReview(Review review);
    @Query("select * from Review where userID = :userID AND recipeID = :recipeID")
    LiveData<List<Review>> getReviewByUserAndRecipe(int userID, int recipeID);
    @Transaction
    @Query("select * from Review")
    LiveData<List<ReviewWithRecipeWithUser>> getAllReviewsWithDetails();
    @Transaction
    @Query("select * from Review where recipeID = :recipeID")
    LiveData<List<ReviewWithRecipeWithUser>> getReviewByRecipeID(int recipeID);
    @Transaction
    @Query("select * from Review where userID= :userID")
    LiveData<List<ReviewWithRecipeWithUser>> getReviewsByUserID(int userID);

}
