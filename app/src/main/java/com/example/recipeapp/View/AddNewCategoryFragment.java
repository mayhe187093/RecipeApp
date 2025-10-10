package com.example.recipeapp.View;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipeapp.Model.Entity.Category;
import com.example.recipeapp.ViewModel.CategoryViewModel;
import com.example.recipeapp.databinding.FragmentAddNewCategoryBinding;

public class AddNewCategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private FragmentAddNewCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddNewCategoryBinding.inflate(inflater,container,false);

        binding.addnewcategory.setOnClickListener(v -> {
            String category = binding.edtcattegory.getText().toString().trim();
            categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
            categoryViewModel.insertCategory(new Category(category));
            AlertDialog.Builder annotation = new AlertDialog.Builder(requireContext());
            annotation.setTitle("Thông báo");
            annotation.setMessage("Thêm thể loại thành công");
            annotation.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    binding.edtcattegory.setText("");
                }
            });
            annotation.show();
        });
        return binding.getRoot();
    }
}