package com.example.recipeapp.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeapp.Model.Entity.Account;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

    private com.example.recipeapp.databinding.ActivityHomePageBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra("account");
        if(account!=null){
            userViewModel.getUserByAccountID(account.getAccountID()).observe(this,user -> {
                userViewModel.setCurrentUser(user);
            });
        }

        loadFragment(new SearchFragment());
        binding.navMenu.setOnItemSelectedListener(menuItem -> {
            getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
            if(menuItem.getItemId() == R.id.nav_search_recipe){
                loadFragment(new SearchFragment());
                return true;
            }
            if(menuItem.getItemId() == R.id.nav_top_recipe){
                loadFragment(new TopRecipeFragment());
                return true;
            }
            if(menuItem.getItemId() == R.id.nav_person_account){
                loadFragment(new PersonAccountFragment());
                return true;
            }
            return false;
        });
    }


    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.homecontent,fragment).commit();
    }
}