    package com.example.recipeapp.View;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Gravity;
    import android.view.View;

    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;

    import com.example.recipeapp.Model.Entity.Account;
    import com.example.recipeapp.Model.Entity.User;
    import com.example.recipeapp.R;
    import com.example.recipeapp.ViewModel.UserViewModel;
    import com.example.recipeapp.databinding.ActivityHomePageBinding;

    import java.util.Locale;

    public class HomePage extends AppCompatActivity {

        private com.example.recipeapp.databinding.ActivityHomePageBinding binding;
        private UserViewModel userViewModel;
        private boolean isLanguageExpanded = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);
            binding = ActivityHomePageBinding.inflate(getLayoutInflater());
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            setContentView(binding.getRoot());
            Intent intent = getIntent();
            Account account = (Account) intent.getSerializableExtra("account");
            if(account!=null){
                userViewModel.getUserByAccountID(account.getAccountID()).observe(this,user -> {
                    userViewModel.setCurrentUser(user);
                });
            }

            loadFragment(new SearchFragment());
            binding.navMenu.setOnItemSelectedListener(menuItem -> {
                if(menuItem.getItemId() == R.id.nav_search_recipe){
                    loadFragment(new SearchFragment());
                    return true;
                }
                if(menuItem.getItemId() == R.id.nav_top_recipe){
                    loadFragment(new TopRecipeFragment());
                    return true;
                }
                if(menuItem.getItemId() == R.id.nav_person_account){
                    loadFragment(new PersonAccountFragment());
                    return true;
                }
                return false;
            });

            binding.btnMenu.setOnClickListener(v -> {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            });

            binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                }
                @Override
                public void onDrawerOpened(@NonNull View drawerView) {
                }
                @Override
                public void onDrawerClosed(@NonNull View drawerView) {
                    if (isLanguageExpanded) {
                        isLanguageExpanded = false;
                        binding.leftMenu.getMenu().setGroupVisible(R.id.group_language, false);
                        binding.leftMenu.invalidate();
                    }
                }
                @Override
                public void onDrawerStateChanged(int newState) {
                }
            });

            binding.leftMenu.setNavigationItemSelectedListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_account){
                    loadFragment(new PersonAccountFragment());
                }
                if (menuItem.getItemId() == R.id.menu_language) {
                    isLanguageExpanded = !isLanguageExpanded;
                    binding.leftMenu.post(() -> {
                        binding.leftMenu.getMenu().setGroupVisible(R.id.group_language, isLanguageExpanded);
                        binding.leftMenu.invalidate();
                    });
                    if(menuItem.getItemId() == R.id.lang_vi){
                        Log.d("Ngôn_Ngữ", "Việt Nam");
                    }
                    if(menuItem.getItemId() == R.id.lang_en){
                        Log.d("Ngôn_Ngữ", "Tiếng Anh");
                    }
                    return false;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }


        public void loadFragment(Fragment fragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.homecontent,fragment).commit();
        }
    }