package com.example.recipeapp.Model.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class RatedRecipe implements Serializable {

    @Embedded
    public Recipe recipe;
    @Relation(
            parentColumn = "categoryID",
            entityColumn = "categoryID"
    )
    public Category category;
    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public User user;
    @Relation(
            parentColumn = "recipeID",
            entityColumn = "recipeID"
    )
    public List<Review> listReview;
}
