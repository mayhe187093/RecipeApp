package com.example.recipeapp.Model.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class RecipeWithUser implements Serializable {

    @Embedded
    public Recipe recipe;
    @Relation(
            parentColumn = "userID",
            entityColumn = "userID"
    )
    public User user;

}
