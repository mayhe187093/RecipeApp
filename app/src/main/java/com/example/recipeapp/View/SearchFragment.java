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
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {
    private AdapterListRecipe adapter;
    private ListView list;
    private ArrayList<RatedRecipe> listSearchRecipe;
    private ArrayList<RatedRecipe> listRecipeAll;
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
        listSearchRecipe = new ArrayList<>();
        listRecipeAll = new ArrayList<>();
        list = binding.listRecipe;
        adapter = new AdapterListRecipe(requireActivity(),R.layout.layout_search, listSearchRecipe);
        list.setAdapter(adapter);

        recipeViewModel.getAllTopRecipeDetail().observe(getViewLifecycleOwner(),recipes -> {
            listRecipeAll.clear();
            listRecipeAll.addAll(recipes);
            listSearchRecipe.clear();
            listSearchRecipe.addAll(recipes);
            adapter.notifyDataSetChanged();
        });

        list.setOnItemClickListener((parent, view, position, id) -> {
            RatedRecipe ratedRecipe = listSearchRecipe.get(position);
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
                    listSearchRecipe.clear();
                    listSearchRecipe.addAll(listRecipeAll);
                }else{
                    ArrayList<RatedRecipe> newListRecipe = filterListRecipe(newText);
                    listSearchRecipe.clear();
                    listSearchRecipe.addAll(newListRecipe);
                }
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    listSearchRecipe.clear();
                    listSearchRecipe.addAll(listRecipeAll);
                }else{
                    ArrayList<RatedRecipe> newListRecipe = filterListRecipe(query);
                    listSearchRecipe.clear();
                    listSearchRecipe.addAll(newListRecipe);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return binding.getRoot();
    }

    public ArrayList<RatedRecipe> filterListRecipe(String nameRecipe){
        ArrayList<RatedRecipe> newListRecipe = listRecipeAll.stream().
                filter(item -> item.recipe.getRecipeName().toLowerCase().contains(nameRecipe.toLowerCase())).
                collect(Collectors.toCollection(ArrayList::new));
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