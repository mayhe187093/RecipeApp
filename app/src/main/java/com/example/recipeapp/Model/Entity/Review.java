package com.example.recipeapp.Model.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "userID",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Recipe.class,
                        parentColumns = "recipeID",
                        childColumns = "recipeID",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Review {
    @PrimaryKey(autoGenerate = true)
    private int reviewID;
    private int rating;
    private String comment;
    private int userID; //
    private int recipeID; //

    public Review(){

    }

    public Review(int rating, String comment, int userID, int recipeID) {
        this.rating = rating;
        this.comment = comment;
        this.userID = userID;
        this.recipeID = recipeID;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
