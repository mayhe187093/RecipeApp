package com.example.recipeapp.View;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Model.Entity.RatedRecipe;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.ReviewWithRecipeWithUser;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.ViewModel.ReviewViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentDetailRecipeBinding;

import java.io.File;
import java.util.ArrayList;

public class DetailRecipeFragment extends Fragment {
    private AdapterListReview adapter;
    private ArrayList<ReviewWithRecipeWithUser> listReview;
    private ListView listDisplay;
    private ReviewViewModel reviewViewModel;
    private FragmentDetailRecipeBinding binding;
    private RecipeViewModel recipeViewModel;
    private User user;
    private UserViewModel userViewModel;
    private RatedRecipe ratedRecipe;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailRecipeBinding.inflate(inflater,container,false);
        recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(),u -> {
            user = u;
            if(ratedRecipe!=null && user!=null){
                if(ratedRecipe.user.getUserID() == user.getUserID()){
                    binding.writeReview.setVisibility(View.GONE);
                }else{
                    binding.writeReview.setVisibility(View.VISIBLE);
                }
            }
        });

        if(getArguments() != null){
            ratedRecipe = (RatedRecipe) getArguments().get("recipeDetail");
        }

        if(ratedRecipe != null){
            Recipe recipe = ratedRecipe.recipe;
            User authorRecipe = ratedRecipe.user;

            if (recipe.getImgPath() != null) {
                File file = new File(recipe.getImgPath());
                Glide.with(requireContext())
                        .load(file)
                        .into(binding.imgDetailRecipe);
            }

            binding.nameAuthorRecipe.setText("Đầu Bếp: " + authorRecipe.getFullName());
            binding.recpieName.setText(recipe.getRecipeName());
            binding.detailIngredient.setText(recipe.getIngredient());
            binding.detailDescription.setText(recipe.getDescription());

            binding.writeReview.setOnClickListener(v -> {
                if (user == null) {
                    Toast.makeText(requireContext(), "Đợi thông tin người dùng...", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder writeReview = new AlertDialog.Builder(requireActivity());
                View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_write_review,null);
                writeReview.setView(viewDialog);
                writeReview.setTitle("Viết Đánh Giá");

                RatingBar ratingBar = viewDialog.findViewById(R.id.ratingBar);
                EditText edtComment = viewDialog.findViewById(R.id.edtComment);

                writeReview.setPositiveButton("Gửi",(dialog, which) -> {
                    int rating = (int) ratingBar.getRating();
                    String comment = edtComment.getText().toString().trim();
                    if(rating == 0 || comment.isEmpty()){
                        Toast.makeText(requireContext(),"Vui Lòng Nhập Đầy Đủ Thông Tin",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    reviewViewModel.insertReview(new Review(rating, comment, user.getUserID(), recipe.getRecipeID()));
                });
                writeReview.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

                writeReview.create().show();
            });

            listReview = new ArrayList<>();
            listDisplay = binding.listReviewRecipe;
            adapter = new AdapterListReview(requireActivity(), R.layout.layout_review_recipe, listReview);
            listDisplay.setAdapter(adapter);

            reviewViewModel.getReviewByRecipeID(recipe.getRecipeID()).observe(getViewLifecycleOwner(), review -> {
                listReview.clear();
                listReview.addAll(review);
                adapter.notifyDataSetChanged();
            });
        }

        return binding.getRoot();
    }
}
