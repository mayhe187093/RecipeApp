package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentPersonAccountBinding;

public class PersonAccountFragment extends Fragment {
    private FragmentPersonAccountBinding binding;
    private UserViewModel userViewModel;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonAccountBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (getArguments() != null && getArguments().getSerializable("authorRecipe") != null) {
            binding.cookByYou.setVisibility(View.GONE);
            binding.createRecipe.setVisibility(View.GONE);

            user = (User) getArguments().getSerializable("authorRecipe");
            binding.userName.setText(user.getFullName());
            binding.userEmail.setText(user.getEmail());

            Bundle bundle = new Bundle();
            bundle.putSerializable("authorRecipe", user);
            CookedRecipes cookedRecipes = new CookedRecipes();
            cookedRecipes.setArguments(bundle);
            loadChildFragment(cookedRecipes);


        } else {
            userViewModel.getCurrentUser().observe(requireActivity(), currentUser -> {
                if (currentUser != null) {
                    user = currentUser;
                    binding.userName.setText(currentUser.getFullName());
                    binding.userEmail.setText(currentUser.getEmail());
                }
            });
            loadChildFragment(new CookedRecipes());
            binding.cookByYou.setOnClickListener(v -> {
                loadChildFragment(new CookedRecipes());
            });
            binding.createRecipe.setOnClickListener(v -> {
                loadChildFragment(new AddNewRecipeFragment());
            });
        }
        return binding.getRoot();
    }

    public void loadChildFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.dataPersonAccount, fragment).commit();
    }
}