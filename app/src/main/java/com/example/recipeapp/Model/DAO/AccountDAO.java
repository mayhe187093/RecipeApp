package com.example.recipeapp.Model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeapp.Model.Entity.Account;

@Dao
public interface AccountDAO {
    @Query("Select * from Account where username = :uname")
    LiveData<Account> getAccountByUserName(String uname);
    @Insert
    void inseartAccount(Account account);
    @Update
    void updateAccount(Account account);
    @Delete
    void deleteAccount(Account account);

}
