package com.example.prepmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prepmate.dashboard.DashboardFragment;
import com.example.prepmate.databinding.ActivityMainBinding;
import com.example.prepmate.findrecipes.FindRecipesFragment;
import com.example.prepmate.home.HomeFragment;
import com.example.prepmate.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is logged in: App should start from sign up if not logged in
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // If the user is not logged in, redirect them to LoginPage
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close HomePage to prevent returning to it
            return;  // Stop further execution
        }


        //METHOD FOR VIEW BINDING
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                // Handle home item
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.dashboard) {
                // Handle dashboard item
                replaceFragment(new DashboardFragment());
            } else if (itemId == R.id.profile) {
                // Handle profile item
                replaceFragment(new ProfileFragment());
            } else if (itemId == R.id.recipe) {
                // Handle profile item
                replaceFragment(new FindRecipesFragment());
            }

            return true; // Return true to display the selected item as the current tab
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

}