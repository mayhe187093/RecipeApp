package com.example.recipeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.Model.Entity.Account;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.Model.Repository.UserRepository;

import java.util.List;
import java.util.concurrent.Future;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<User> currentAccount = new MutableLiveData<>();
    private UserRepository userRepository;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void inseartUser(User user){
        userRepository.inseartUser(user);
    }

    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }

    public LiveData<List<User>> getAllUser(){
        return userRepository.getAllUser();
    }

    public LiveData<User> getUserByAccountID(int id){
        return userRepository.getUserByAccountID(id);
    }

    public void setCurrentUser(User user){
        currentAccount.setValue(user);
    }

    public LiveData<User> getCurrentUser(){
        return currentAccount;
    }

    public User UserByAccountID(int id){
        return userRepository.UserByAccountID(id);
    }
}
