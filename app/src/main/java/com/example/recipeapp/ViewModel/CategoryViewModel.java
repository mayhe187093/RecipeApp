package com.example.recipeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeapp.Model.Entity.Category;
import com.example.recipeapp.Model.Repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategory;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategory = categoryRepository.getAllCategory();
    }
    public LiveData<List<Category>> getAllCategory(){
        return allCategory;
    }
    public void insertCategory(Category category){
        categoryRepository.insertCategory(category);
    }
    public void updateCategory(Category category){
        categoryRepository.updateCategory(category);
    }
    public void deleteCategory(Category category){
        categoryRepository.deleteCategory(category);
    }
}
