package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentCookedRecipesBinding;

import java.util.ArrayList;

public class CookedRecipes extends Fragment {

    private RecyclerView recyclerView;
    private FragmentCookedRecipesBinding binding;
    private ArrayList<RatedRecipe> list;
    private AdapterCookedRecipe adapter;
    private RecipeViewModel recipeViewModel;
    private UserViewModel userViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCookedRecipesBinding.inflate(inflater,container,false);
        recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        list = new ArrayList<>();
        recyclerView = binding.cookedRecipes;
        binding.cookedRecipes.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        adapter = new AdapterCookedRecipe(list,getParentFragment(),recipeViewModel);
        binding.cookedRecipes.setAdapter(adapter);
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(),user -> {
            if(user!=null){
                recipeViewModel.getRatedRecipeByUserID(user.getUserID()).observe(getViewLifecycleOwner(),listRatedRecipes->{
                    list.clear();
                    list.addAll(listRatedRecipes);
                    Log.d("aaaaaaaa", listRatedRecipes.size()+"");
                    adapter.notifyDataSetChanged();
                });
            }
        });
        adapter.setOnItemClickListenerAdapter(ratedRecipe -> {
            recipeViewModel.getTopRecipeDetailByID(ratedRecipe.recipe.getRecipeID()).observe(getViewLifecycleOwner(),recipeDetail ->{
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipeDetail",recipeDetail);
                DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
                detailRecipeFragment.setArguments(bundle);
                loadFragment(detailRecipeFragment);
            } );
        });
        return binding.getRoot();
    }

    public void loadFragment(Fragment fragment){
        requireActivity().
                getSupportFragmentManager().
                beginTransaction().
                replace(R.id.homecontent,fragment).
                addToBackStack(null).
                commit();
    }
}