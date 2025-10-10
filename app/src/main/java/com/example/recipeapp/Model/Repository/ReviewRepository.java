package com.example.recipeapp.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.DAO.ReviewDAO;
import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.ReviewWithRecipeWithUser;

import java.util.List;

public class ReviewRepository {
    private ReviewDAO reviewDAO;
    public ReviewRepository(Application application){
        DBConnection db = DBConnection.getINSTANCE(application);
        reviewDAO = db.createReviewDao();
    }
    public void insertReview(Review review){
        DBConnection.databaseWriteExecutor.execute(() -> reviewDAO.insertReview(review));
    }
    public void updateReview(Review review){
        DBConnection.databaseWriteExecutor.execute(() -> reviewDAO.updateReview(review));
    }
    public void deleteReview(Review review){
        DBConnection.databaseWriteExecutor.execute(() -> reviewDAO.deleteReview(review));
    }
    public LiveData<List<ReviewWithRecipeWithUser>> getReviewByRecipeID(int recipeID){
        return reviewDAO.getReviewByRecipeID(recipeID);
    }
    public LiveData<List<ReviewWithRecipeWithUser>> getReviewsByUserID(int userID){
        return reviewDAO.getReviewsByUserID(userID);
    }
}
