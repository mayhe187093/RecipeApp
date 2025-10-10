package com.example.recipeapp.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.DAO.CategoryDAO;
import com.example.recipeapp.Model.Entity.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private LiveData<List<Category>> allCategory;
    public CategoryRepository(Application application){
        DBConnection db = DBConnection.getINSTANCE(application);
        categoryDAO = db.createCategoryDao();
        allCategory = categoryDAO.getAllCategory();
    }
    public void insertCategory(Category category){
        DBConnection.databaseWriteExecutor.execute(() -> categoryDAO.insertCategory(category));
    }
    public void updateCategory(Category category){
        DBConnection.databaseWriteExecutor.execute(() -> categoryDAO.updateCategory(category));
    }
    public void deleteCategory(Category category){
        DBConnection.databaseWriteExecutor.execute(() -> categoryDAO.deleteCategory(category));
    }
    public LiveData<List<Category>> getAllCategory(){
        return allCategory;
    }
}
