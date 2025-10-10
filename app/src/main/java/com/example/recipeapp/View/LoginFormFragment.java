package com.example.recipeapp.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipeapp.ViewModel.AccountViewModel;
import com.example.recipeapp.ViewModel.UserViewModel;
import com.example.recipeapp.databinding.FragmentLoginFormBinding;


public class LoginFormFragment extends Fragment {
    private AccountViewModel accountViewModel;
    private UserViewModel userViewModel;
    private FragmentLoginFormBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginFormBinding.inflate(inflater,container,false);

        binding.btnlogin.setOnClickListener(v -> {

            String uname = binding.edtuname.getText().toString().trim();
            String pass = binding.edtpassword.getText().toString();
            accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
            userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            accountViewModel.getAccountByUserName(uname).observe(getViewLifecycleOwner(),account -> {
                if(account == null){
                    Toast.makeText(requireActivity(),"Thông tin tài khoản mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
                }else{
                    if(uname.equals("ATPX4869")){
                        userViewModel.getUserByAccountID(account.getAccountID()).observe(requireActivity(),user -> {
                            if(user!=null){
                                userViewModel.setCurrentUser(user);
                                Intent intent = new Intent(requireActivity(), AdminPage.class);
                                intent.putExtra("account",account);
                                startActivity(intent);
                            }
                        });

                    }else{
                        userViewModel.getUserByAccountID(account.getAccountID()).observe(requireActivity(),user -> {
                            if(user!=null){
                                userViewModel.setCurrentUser(user);
                                Intent intent = new Intent(requireActivity(), HomePage.class);
                                intent.putExtra("account",account);
                                startActivity(intent);
                            }
                        });

                    }
                    requireActivity().finish();
                }
            });
        });

        return binding.getRoot();
    }
}