package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.Model.Entity.FavoriteRecipe;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.FavoriteRecipeViewModel;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentFavoriteRecipeBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterListFavoriteRecipe adapterListFavoriteRecipe;
    private FragmentFavoriteRecipeBinding binding;
    private FavoriteRecipeViewModel favoriteRecipeViewModel;
    private RecipeViewModel recipeViewModel;
    private UserViewModel userViewModel;
    private User user;
    private List<RatedRecipe> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteRecipeBinding.inflate(inflater, container, false);
        favoriteRecipeViewModel = new ViewModelProvider(requireActivity()).get(FavoriteRecipeViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        list = new ArrayList<>();

        recyclerView = binding.listFavoriteRecipe;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapterListFavoriteRecipe = new AdapterListFavoriteRecipe(list, requireActivity(), recipeViewModel);
        recyclerView.setAdapter(adapterListFavoriteRecipe);

        userViewModel.getCurrentUser().observe(requireActivity(), cur -> {
            user = cur;
            recipeViewModel.getListFavoriteRecipeByUserID(cur.getUserID(), true).observe(requireActivity(), listFavorite -> {
                adapterListFavoriteRecipe.setData(listFavorite);
            });
        });

        adapterListFavoriteRecipe.setOnItemListener(recipe -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipeDetail", recipe);
            DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
            detailRecipeFragment.setArguments(bundle);
            loadFragment(detailRecipeFragment);
        });

        return binding.getRoot();
    }

    public void loadFragment(Fragment fragment){
        // sử dụng khi fragment này gắn vào activity
        requireActivity().
                getSupportFragmentManager().
                beginTransaction().
                replace(R.id.homecontent, fragment).
                addToBackStack(null).
                commit();
    }

}