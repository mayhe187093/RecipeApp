package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.recipeapp.Model.Entity.Category;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.TopRecipe;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.CategoryViewModel;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.databinding.FragmentTopRecipeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class TopRecipeFragment extends Fragment {
    private AdapterTopRecipe adapterTopRecipe;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<Category> adapterCategory;
    private CategoryViewModel categoryViewModel;
    private RecyclerView recyclerViewTop3Recipe;
    private RecyclerView recyclerViewTopRecipe;
    private AdapterTop3Recipe adapterTop3Recipe;
    private ArrayList<TopRecipe> listAll;
    private ArrayList<TopRecipe> listOthers;
    private ArrayList<TopRecipe> listTop3;
    private RecipeViewModel recipeViewModel;
    private FragmentTopRecipeBinding binding;
    private RatedRecipe recipe;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTopRecipeBinding.inflate(inflater, container, false);
        recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        listAll = new ArrayList<>();
        listOthers = new ArrayList<>();
        listTop3 = new ArrayList<>();

        autoCompleteTextView = binding.dropdownCategory;
        //
        recyclerViewTopRecipe = binding.listTopRecipe;
        recyclerViewTopRecipe.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        adapterTopRecipe = new AdapterTopRecipe(requireActivity(),listOthers);
        recyclerViewTopRecipe.setAdapter(adapterTopRecipe);
        //
        recyclerViewTop3Recipe = binding.top3RecipeReview;
        recyclerViewTop3Recipe.setLayoutManager(
                new GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
        );
        adapterTop3Recipe = new AdapterTop3Recipe(requireActivity(), listTop3);
        recyclerViewTop3Recipe.setAdapter(adapterTop3Recipe);

        categoryViewModel.getAllCategory().observe(requireActivity(),list -> {
            if(list.get(0).getCategoryID() != 0){
                list.add(new Category());
                list.get(list.size()-1).setCategoryID(0);
                list.get(list.size()-1).setCategoryName("Tất cả loại");
                Collections.reverse(list);
            }
            if (list!=null){
                adapterCategory = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        list
                );
            }
            autoCompleteTextView.setAdapter(adapterCategory);
        });

        recipeViewModel.getTopRecipe().observe(getViewLifecycleOwner(), topRecipes -> {
            listAll.clear();
            listAll.addAll(topRecipes);
            filterListRecipe((ArrayList<TopRecipe>) topRecipes);
            adapterTop3Recipe.notifyDataSetChanged();
            adapterTopRecipe.notifyDataSetChanged();
        });

        binding.filter.setOnClickListener(v -> {
            String categoryName = autoCompleteTextView.getText().toString();
            ArrayList<TopRecipe> listFilter;
            if (categoryName.isEmpty() || categoryName.equals("Tất cả loại")) {
                filterListRecipe(listAll);
            } else {
                listFilter = new ArrayList<>(
                        listAll.stream()
                                .filter(item -> item.getCategoryName().equals(autoCompleteTextView.getText().toString()))
                                .collect(Collectors.toList())
                );
                filterListRecipe(listFilter);
            }
            adapterTop3Recipe.notifyDataSetChanged();
            adapterTopRecipe.notifyDataSetChanged();
        });

        adapterTopRecipe.setOnItemListener(topRecipe -> {
            int recipeID = topRecipe.getRecipeID();
            recipeViewModel.getTopRecipeDetailByID(recipeID).observe(getViewLifecycleOwner(),
                    topRecipeDetail -> {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("recipeDetail", topRecipeDetail);
                        DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
                        detailRecipeFragment.setArguments(bundle);
                        loadFragment(detailRecipeFragment);
                    });
        });
        adapterTop3Recipe.setOnItemListener(topRecipe -> {
            int recipeID = topRecipe.getRecipeID();
            recipeViewModel.getTopRecipeDetailByID(recipeID).observe(getViewLifecycleOwner(),
                    topRecipeDetail -> {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("recipeDetail", topRecipeDetail);
                        DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
                        detailRecipeFragment.setArguments(bundle);
                        loadFragment(detailRecipeFragment);
                    });
        });

        return binding.getRoot();
    }

    public void loadFragment(Fragment fragment) {
        // sử dụng khi fragment này muốn chuyển fragment khác (bản chuẩn)
        requireActivity().
                getSupportFragmentManager().
                beginTransaction().
                replace(R.id.homecontent, fragment).
                addToBackStack(null).
                commit();
    }

    public void filterListRecipe(ArrayList<TopRecipe> list) {
        if (list.size() > 3) {
            listTop3.clear();
            listTop3.addAll(list.subList(0, 3));
            listOthers.clear();
            listOthers.addAll(list.subList(3, list.size()));
        } else {
            listTop3.clear();
            listTop3.addAll(list.subList(0, list.size()));
            listOthers.clear();
        }
    }
}