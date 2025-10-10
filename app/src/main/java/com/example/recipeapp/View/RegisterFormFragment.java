package com.example.recipeapp.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeapp.Model.Entity.Account;
import com.example.recipeapp.Model.Entity.User;
import com.example.recipeapp.R;
import com.example.recipeapp.ViewModel.AccountViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentRegisterFormBinding;

public class RegisterFormFragment extends Fragment {

    private FragmentRegisterFormBinding binding;
    private AccountViewModel accountViewModel;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterFormBinding.inflate(inflater,container,false);

        binding.btnsave.setOnClickListener(v -> {

            accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
            userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            String uname = binding.suuname.getText().toString().trim();
            String pass = binding.supassword.getText().toString();
            String confirmPass = binding.supassword1.getText().toString();
            String fname = binding.sufname.getText().toString().trim();
            String email = binding.suemail.getText().toString().trim();

            boolean valid = true;

            if(!accountViewModel.isEmailValid(email)){
                binding.suemail.setError("Email không hợp lệ");
                valid = false;
            }
            if(!accountViewModel.isPasswordValid(pass)){
                binding.supassword.setError("Mật khẩu yếu");
                valid = false;
            }
            if(!pass.equals(confirmPass)){
                binding.supassword1.setError("Mật khẩu không trùng nhau");
                valid = false;
            }
            if(fname.length() > 20){
                binding.sufname.setError("Tên người dùng quá dài");
                valid = false;
            }
            if(valid){
                accountViewModel.getAccountByUserName(uname).observe(getViewLifecycleOwner(),account -> {
                    if(account == null){
                        accountViewModel.inseartAccount(new Account(uname,pass));
                        accountViewModel.getAccountByUserName(uname).observe(getViewLifecycleOwner(),newaccount ->{
                            if(newaccount != null){
                                accountViewModel.getAccountByUserName(uname).removeObservers(getViewLifecycleOwner());
                                userViewModel.inseartUser(new User(newaccount.getAccountID(),fname,email));
                                Toast.makeText(requireActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                userViewModel.getUserByAccountID(newaccount.getAccountID()).observe(getViewLifecycleOwner(),newUser->{
                                    if(newUser!=null){
                                        userViewModel.setCurrentUser(newUser);
                                    }
                                });
                                Intent intnent = new Intent(requireActivity(), HomePage.class);
                                startActivity(intnent);
                                requireActivity().finish();
                            }

                        });

                    }else{
                        binding.suuname.setError("Username đã tồn tại");
                    }
                });
            }
        });

        return binding.getRoot();
    }

}