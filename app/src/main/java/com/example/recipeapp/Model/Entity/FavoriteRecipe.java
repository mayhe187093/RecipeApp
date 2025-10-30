package com.example.recipeapp.Model.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (foreignKeys = {
        @ForeignKey(
                entity = Recipe.class,
                parentColumns = "recipeID",
                childColumns = "recipeID",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "userID",
                childColumns = "userID",
                onDelete = ForeignKey.CASCADE
        )
})
public class FavoriteRecipe {
    @PrimaryKey(autoGenerate = true)
    private int favoriteRecipeID;
    private int recipeID;
    private int userID;
    private boolean favorite;

    public FavoriteRecipe(){

    }

    public int getFavoriteRecipeID() {
        return favoriteRecipeID;
    }

    public void setFavoriteRecipeID(int favoriteRecipeID) {
        this.favoriteRecipeID = favoriteRecipeID;
    }

    public FavoriteRecipe(int recipeID, int userID, boolean favorite) {
        this.recipeID = recipeID;
        this.userID = userID;
        this.favorite = favorite;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
