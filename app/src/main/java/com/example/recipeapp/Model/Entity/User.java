package com.example.recipeapp.Model.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(
        entity = Account.class,
        parentColumns = "accountID",
        childColumns = "accountID",
        onDelete = ForeignKey.CASCADE
))
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int userID;
    private int accountID;
    private String fullName;
    private String email;

    public User() {

    }

    public User(int accountID, String fullName, String email) {
        this.accountID = accountID;
        this.fullName = fullName;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
