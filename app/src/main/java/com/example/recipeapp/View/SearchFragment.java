package com.example.recipeapp.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.RecipeWithUser;
import com.example.recipeapp.Model.Entity.TopRecipeDetail;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private AdapterListRecipe adapter;
    private ListView list;
    private ArrayList<TopRecipeDetail> listTopRecipeDetail;
    private ArrayList<TopRecipeDetail> listTopRecipeDetailAll;
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
        listTopRecipeDetail = new ArrayList<>();
        listTopRecipeDetailAll = new ArrayList<>();
        list = binding.listRecipe;
        adapter = new AdapterListRecipe(requireActivity(),R.layout.layout_search,listTopRecipeDetail);
        list.setAdapter(adapter);

        recipeViewModel.getAllTopRecipeDetail().observe(getViewLifecycleOwner(),recipes -> {
            listTopRecipeDetailAll.clear();
            listTopRecipeDetailAll.addAll(recipes);
            listTopRecipeDetail.clear();
            listTopRecipeDetail.addAll(recipes);
            adapter.notifyDataSetChanged();
        });

        list.setOnItemClickListener((parent, view, position, id) -> {
            TopRecipeDetail topRecipeDetail = listTopRecipeDetail.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipeDetail",topRecipeDetail);
            DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
            detailRecipeFragment.setArguments(bundle);
            loadFragment(detailRecipeFragment);
        });

        binding.searchrecipe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    listTopRecipeDetail.clear();
                    listTopRecipeDetail.addAll(listTopRecipeDetailAll);
                }else{
                    ArrayList<TopRecipeDetail> newListRecipe = filterListRecipe(newText);
                    listTopRecipeDetail.clear();
                    listTopRecipeDetail.addAll(newListRecipe);
                }
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    listTopRecipeDetail.clear();
                    listTopRecipeDetail.addAll(listTopRecipeDetailAll);
                }else{
                    ArrayList<TopRecipeDetail> newListRecipe = filterListRecipe(query);
                    listTopRecipeDetail.clear();
                    listTopRecipeDetail.addAll(newListRecipe);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return binding.getRoot();
    }

    public ArrayList<TopRecipeDetail> filterListRecipe(String nameRecipe){
        ArrayList<TopRecipeDetail> newListRecipe = new ArrayList<>();
        for (TopRecipeDetail recipe: listTopRecipeDetailAll) {
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
                addToBackStack(null).
                replace(R.id.homecontent,fragment).
                addToBackStack(null).
                commit();
    }

}