package com.example.recipeapp.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipeapp.Database.DBConnection;
import com.example.recipeapp.Model.DAO.AccountDAO;
import com.example.recipeapp.Model.Entity.Account;

import java.util.List;

public class AccountRepository {
    private AccountDAO accountDAO;

    public AccountRepository(Application application){
        DBConnection db = DBConnection.getINSTANCE(application);
        accountDAO = db.createAccountDao();
    }

    public void inseartAccount(Account account){
        DBConnection.databaseWriteExecutor.execute(() -> accountDAO.inseartAccount(account));
    }
    public void updateAccount(Account account){
        DBConnection.databaseWriteExecutor.execute(() -> accountDAO.updateAccount(account));
    }
    public void deleteAccount(Account account){
        DBConnection.databaseWriteExecutor.execute(() -> accountDAO.deleteAccount(account));
    }

    public LiveData<Account> getAccountByUserName(String uname){
        return accountDAO.getAccountByUserName(uname);
    }
}
