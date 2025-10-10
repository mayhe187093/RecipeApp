package com.example.recipeapp.Model.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "categoryID",
                        childColumns = "categoryID",
                        onDelete = ForeignKey.CASCADE
                ),

                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "userID",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int recipeID;
    private int userID; //
    private String recipeName;
    private int categoryID; //
    private String ingredient;
    private String description;

    private String imgPath;

    public Recipe(){

    }

    public Recipe(int userID, String recipeName, int categoryID, String ingredient, String description, String imgPath) {
        this.userID = userID;
        this.recipeName = recipeName;
        this.categoryID = categoryID;
        this.ingredient = ingredient;
        this.description = description;
        this.imgPath = imgPath;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
