package com.tuhoc.chatapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.tuhoc.chatapp.R;
import com.tuhoc.chatapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bnvNav, navController);


        binding.bnvNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.chatFragment:
                    navController.navigate(R.id.chatFragment);
                    return true;
                case R.id.callFragment:
                    navController.navigate(R.id.callFragment);
                    return true;
                case R.id.phoneBookFragment:
                    navController.navigate(R.id.phoneBookFragment);
                    return true;
                case R.id.newsFragment:
                    navController.navigate(R.id.newsFragment);
                    return true;
            }
            return false;
        });
    }
}