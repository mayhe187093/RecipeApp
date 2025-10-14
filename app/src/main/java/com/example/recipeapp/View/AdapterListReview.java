package com.example.recipeapp.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.recipeapp.Model.Entity.ReviewWithRecipeWithUser;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.LayoutReviewRecipeBinding;

import java.util.ArrayList;

public class AdapterListReview extends ArrayAdapter<ReviewWithRecipeWithUser> {
    private final Activity context;
    private final int IDLayout;
    private final ArrayList<ReviewWithRecipeWithUser> list;
    private LayoutReviewRecipeBinding binding;

    public AdapterListReview(@NonNull Activity context, int IDLayout, @NonNull ArrayList<ReviewWithRecipeWithUser> list) {
        super(context, IDLayout, list);
        this.context = context;
        this.list = list;
        this.IDLayout = IDLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            binding = LayoutReviewRecipeBinding.inflate(inflater,parent,false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (LayoutReviewRecipeBinding) convertView.getTag();
        }

        ReviewWithRecipeWithUser review = list.get(position);

        binding.nameReviewer.setText(review.user.getFullName());
        binding.ratingReview.setText(String.valueOf(review.review.getRating()) + "★");
        binding.commentReview.setText(review.review.getComment());

        return convertView;
    }
}
