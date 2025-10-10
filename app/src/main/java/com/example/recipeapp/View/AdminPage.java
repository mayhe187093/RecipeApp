package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.recipeapp.R;
import com.example.recipeapp.databinding.ActivityAdminPageBinding;

public class AdminPage extends AppCompatActivity {

    private ActivityAdminPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_admin_page);
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(new AddNewCategoryFragment());

        binding.navMain.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(R.id.nav_category_main == id){
                loadFragment(new AddNewCategoryFragment());
                return true;
            }
            if(R.id.nav_recipe_main == id){
                loadFragment(new AddNewRecipeFragment());
                return true;
            }
            if(R.id.nav_review_main == id){
                return true;
            }
            return false;
        });

    }


    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.admincontent,fragment).commit();
    }

}