package com.example.recipeapp.Model.Entity;

public class TopRecipe {
    private double avgRating;
    private int numberOfReviews;
    private int recipeID;
    private String recipeName;
    private String fullName;
    private String categoryName;
    private String ingredient;
    private String description;
    private String imgPath;

    public TopRecipe (){

    }
    public TopRecipe(double avgRating, int numberOfReviews, int recipeID,
                     String recipeName, String fullName, String categoryName,
                     String ingredient, String description, String imgPath) {
        this.avgRating = avgRating;
        this.numberOfReviews = numberOfReviews;
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.fullName = fullName;
        this.categoryName = categoryName;
        this.ingredient = ingredient;
        this.description = description;
        this.imgPath = imgPath;
    }
    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
