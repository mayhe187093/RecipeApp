package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private AdapterListRecipe adapter;
    private ListView list;
    private ArrayList<RatedRecipe> listRatedRecipe;
    private ArrayList<RatedRecipe> listRatedRecipeAll;
    private RecipeViewModel recipeViewModel;
    private UserViewModel userViewModel;
    private User user;
    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        listRatedRecipe = new ArrayList<>();
        listRatedRecipeAll = new ArrayList<>();
        list = binding.listRecipe;
        adapter = new AdapterListRecipe(requireActivity(),R.layout.layout_search, listRatedRecipe);
        list.setAdapter(adapter);

        recipeViewModel.getAllTopRecipeDetail().observe(getViewLifecycleOwner(),recipes -> {
            listRatedRecipeAll.clear();
            listRatedRecipeAll.addAll(recipes);
            listRatedRecipe.clear();
            listRatedRecipe.addAll(recipes);
            adapter.notifyDataSetChanged();
        });

        list.setOnItemClickListener((parent, view, position, id) -> {
            RatedRecipe ratedRecipe = listRatedRecipe.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipeDetail", ratedRecipe);
            DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
            detailRecipeFragment.setArguments(bundle);
            loadFragment(detailRecipeFragment);
        });

        binding.searchrecipe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    listRatedRecipe.clear();
                    listRatedRecipe.addAll(listRatedRecipeAll);
                }else{
                    ArrayList<RatedRecipe> newListRecipe = filterListRecipe(newText);
                    listRatedRecipe.clear();
                    listRatedRecipe.addAll(newListRecipe);
                }
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    listRatedRecipe.clear();
                    listRatedRecipe.addAll(listRatedRecipeAll);
                }else{
                    ArrayList<RatedRecipe> newListRecipe = filterListRecipe(query);
                    listRatedRecipe.clear();
                    listRatedRecipe.addAll(newListRecipe);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return binding.getRoot();
    }

    public ArrayList<RatedRecipe> filterListRecipe(String nameRecipe){
        ArrayList<RatedRecipe> newListRecipe = new ArrayList<>();
        for (RatedRecipe recipe: listRatedRecipeAll) {
            if(recipe.recipe.getRecipeName().toLowerCase().contains(nameRecipe.toLowerCase())){
                newListRecipe.add(recipe);
            }
        }
        return newListRecipe;
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