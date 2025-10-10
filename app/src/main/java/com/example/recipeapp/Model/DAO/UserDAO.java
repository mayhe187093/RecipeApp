package com.example.recipeapp.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeapp.Model.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("select * from User where accountID = :id")
    LiveData<User> getUserByAccountID(int id);
    @Query("select * from User where accountID = :id")
    User UserByAccountID(int id);
    @Insert
    void inseartUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);
    @Query("select * from User")
    LiveData<List<User>> getAllUser();
}
