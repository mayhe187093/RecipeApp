package com.example.recipeapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.recipeapp.R;
import com.example.recipeapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(new LoginFormFragment());

        binding.signin.setOnClickListener(v -> {
            loadFragment(new LoginFormFragment());
        });

        binding.signup.setOnClickListener(v -> {
            loadFragment(new RegisterFormFragment());
        });
    }

    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.loginform,fragment).commit();
    }

}