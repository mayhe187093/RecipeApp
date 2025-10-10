package com.example.recipeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipeapp.Model.Entity.Account;
import com.example.recipeapp.Model.Repository.AccountRepository;

import kotlin.text.Regex;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    public void inseartAccount(Account account){
        accountRepository.inseartAccount(account);
    }

    public void updateAccount(Account account){
        accountRepository.updateAccount(account);
    }

    public void deleteAccount(Account account){
        accountRepository.deleteAccount(account);
    }
    public LiveData<Account> getAccountByUserName(String uname){
        return accountRepository.getAccountByUserName(uname);
    }

    public boolean isPasswordValid(String password){
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")){
            return true;
        }
        return false;
    }

    public boolean isEmailValid(String email){
        if(email.matches("^[a-zA-Z0-9]+@[.a-zA-Z]+$")){
            return true;
        }
        return false;
    }

}
