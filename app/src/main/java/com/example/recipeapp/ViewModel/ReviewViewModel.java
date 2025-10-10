package com.example.recipeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.ReviewWithRecipeWithUser;
import com.example.recipeapp.Model.Repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository reviewRepository;
    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
    }
    public void insertReview(Review review){
        reviewRepository.insertReview(review);
    }
    public void updateReview(Review review){
        reviewRepository.updateReview(review);
    }
    public void deleteReview(Review review){
        deleteReview(review);
    }
    public LiveData<List<ReviewWithRecipeWithUser>> getReviewByRecipeID(int recipeID){
        return reviewRepository.getReviewByRecipeID(recipeID);
    }
    public LiveData<List<ReviewWithRecipeWithUser>> getReviewsByUserID(int userID){
        return reviewRepository.getReviewsByUserID(userID);
    }
}
