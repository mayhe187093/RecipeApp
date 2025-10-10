package com.example.recipeapp.Model.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReviewWithRecipeWithUser {
    @Embedded
    public Review review;

    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public User user;
    @Relation(
            parentColumn = "recipeID",
            entityColumn = "recipeID"
    )
    public Recipe recipe;
}
