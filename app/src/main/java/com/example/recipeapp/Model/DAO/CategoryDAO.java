package com.example.recipeapp.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeapp.Model.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(Category category);
    @Update
    void updateCategory(Category category);
    @Delete
    void deleteCategory(Category category);
    @Query("select * from Category")
    LiveData<List<Category>> getAllCategory();
}
