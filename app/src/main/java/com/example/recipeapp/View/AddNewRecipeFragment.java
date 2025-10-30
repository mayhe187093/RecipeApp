package com.example.recipeapp.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.recipeapp.Model.Entity.Account;
import com.example.recipeapp.Model.Entity.Category;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.CategoryViewModel;
import com.example.recipeapp.ViewModel.RecipeViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentAddNewRecipeBinding;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class AddNewRecipeFragment extends Fragment {

    private FragmentAddNewRecipeBinding binding;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<Category> adapter;
    private RecipeViewModel recipeViewModel;
    private UserViewModel userViewModel;
    private CategoryViewModel categoryViewModel;
    private String imgPath;
    //load anh
    private ActivityResultLauncher<String> pickImgLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imgPath = saveImageToAppStorage(uri);
                    if (imgPath != null) {
                        binding.imgRecipe.setImageURI(Uri.fromFile(new File(imgPath)));
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewRecipeBinding.inflate(inflater, container, false);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        autoCompleteTextView = binding.autoCategory;

        categoryViewModel.getAllCategory().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        categories
                );
                autoCompleteTextView.setAdapter(adapter);
            }
        });

        binding.addImg.setOnClickListener(v -> pickImgLauncher.launch("image/*"));

        binding.addNewRecipe.setOnClickListener(v -> {
            Intent intent = requireActivity().getIntent();
            Account account = (Account) intent.getSerializableExtra("account");

            String nameRecipe = binding.edtNameRecipe.getText().toString().trim();
            String ingredient = binding.edtIngredient.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();
            String categoryName = autoCompleteTextView.getText().toString();

            if(nameRecipe.isEmpty() || ingredient.isEmpty() || description.isEmpty() || categoryName.isEmpty()||imgPath==null){
                Toast.makeText(requireContext(),"Vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                return;
            }

            userViewModel.getUserByAccountID(account.getAccountID()).observe(getViewLifecycleOwner(), user -> {
                Category category = new Category();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (categoryName.equals(adapter.getItem(i).getCategoryName())) {
                        category = adapter.getItem(i);
                        break;
                    }
                }
                if (category == null) {
                    Toast.makeText(requireContext(), "Vui lòng chọn loại món ăn", Toast.LENGTH_SHORT).show();
                    return;
                }
                recipeViewModel.insertRecipe(new Recipe(user.getUserID(), nameRecipe, category.getCategoryID(),
                        ingredient, description, imgPath));
                AlertDialog.Builder annotation = new AlertDialog.Builder(requireContext());
                annotation.setTitle("Thông Báo");
                annotation.setMessage("Thêm thành công món mới");
                annotation.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        binding.edtIngredient.setText("");
                        binding.edtDescription.setText("");
                        binding.edtNameRecipe.setText("");
                        autoCompleteTextView.setText("");
                        imgPath = null;
                        binding.imgRecipe.setImageResource(R.drawable.img_add_new_recipe);
                    }
                });
                annotation.show();
            });


        });


        return binding.getRoot();
    }

    // copy ảnh vào thư mục riêng của app
    private String saveImageToAppStorage(Uri uri) {
        try (InputStream inputStream = requireContext().getContentResolver().openInputStream(uri)) {
            File file = new File(requireContext().getFilesDir(),
                    "recipe_" + System.currentTimeMillis() + ".jpg");
            try (OutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}