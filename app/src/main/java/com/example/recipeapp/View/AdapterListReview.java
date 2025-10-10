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

import java.util.ArrayList;

public class AdapterListReview extends ArrayAdapter<ReviewWithRecipeWithUser> {
    private final Activity context;
    private final int IDLayout;
    private final ArrayList<ReviewWithRecipeWithUser> list;

    public AdapterListReview(@NonNull Activity context, int IDLayout, @NonNull ArrayList<ReviewWithRecipeWithUser> list) {
        super(context, IDLayout, list);
        this.context = context;
        this.list = list;
        this.IDLayout = IDLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(IDLayout, null);

        ReviewWithRecipeWithUser review = list.get(position);

        TextView nameReviewer = rowView.findViewById(R.id.nameReviewer);
        TextView ratingRecipe = rowView.findViewById(R.id.ratingReview);
        TextView commentReview = rowView.findViewById(R.id.commentReview);

        nameReviewer.setText(review.user.getFullName());
        ratingRecipe.setText(String.valueOf(review.review.getRating()) + "★");
        commentReview.setText(review.review.getComment());

        return rowView;
    }
}
