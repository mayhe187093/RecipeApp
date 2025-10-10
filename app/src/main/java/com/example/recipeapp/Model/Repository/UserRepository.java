package com.example.recipeapp.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.DAO.UserDAO;
import com.example.recipeapp.Model.Entity.User;

import java.util.List;
import java.util.concurrent.Future;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> allUser;

    public UserRepository(Application application){
        DBConnection db = DBConnection.getINSTANCE(application);
        userDAO = db.createUserDao();
        allUser = userDAO.getAllUser();
    }

    public void inseartUser(User user){
        DBConnection.databaseWriteExecutor.execute(()->userDAO.inseartUser(user));
    }
    public void updateUser(User user){
        DBConnection.databaseWriteExecutor.execute(()->userDAO.updateUser(user));
    }
    public void deleteUser(User user){
        DBConnection.databaseWriteExecutor.execute(()->userDAO.deleteUser(user));
    }

    public LiveData<List<User>> getAllUser(){
        return allUser;
    }

    public LiveData<User> getUserByAccountID(int id){
        return userDAO.getUserByAccountID(id);
    }

    public User UserByAccountID(int id){
        return userDAO.UserByAccountID(id);
    }
}
