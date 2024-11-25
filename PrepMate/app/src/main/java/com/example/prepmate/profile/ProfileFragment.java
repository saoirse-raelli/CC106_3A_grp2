package com.example.prepmate.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prepmate.AboutPrepMate;
import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.LoginActivity;
import com.example.prepmate.R;

public class ProfileFragment extends Fragment {

    private TextView nameTextView, firstNameTextView, lastNameTextView;
    private Button logoutButton, btnAbout;
    private DatabaseHelper databaseHelper;
    SwitchCompat switchCompat;
    boolean isNightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Initialize
        SwitchCompat switchCompat = view.findViewById(R.id.switchCompat);
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        editor = sharedPreferences.edit();

        isNightMode =sharedPreferences.getBoolean("nightmode", false);

        if (isNightMode) {
            switchCompat.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        // on and off night mode
        switchCompat.setOnClickListener(v ->{
            nyThemes();
        });

        // Initialize views
        nameTextView = view.findViewById(R.id.nameTextView);
        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        logoutButton = view.findViewById(R.id.btnLogout);
        btnAbout = view.findViewById(R.id.btnAbout);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(getContext());

        // Retrieve logged-in user ID from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // Get the user_id, default is -1 if not logged in

        if (userId != -1) {
            displayUserDetails(userId); // Fetch and display user details using the user_id
        } else {
            nameTextView.setText("User Not Found");
            firstNameTextView.setText("");
            lastNameTextView.setText("");
        }

        // Set up the logout button click listener
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());
        btnAbout.setOnClickListener(v -> {
            // Create an Intent to go to the AboutPrepMateActivity
            Intent intent = new Intent(getActivity(), AboutPrepMate.class);
            startActivity(intent);  // Start the activity
        });

        return view;
    }

    private void nyThemes() {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("nightmode", false);
            isNightMode = false;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("nightmode", true);
            isNightMode = true;
        }
        editor.apply();
    }


    private void displayUserDetails(int userId) {
        // Fetch user details from the database using user_id
        Cursor cursor = databaseHelper.getUserDetailsById(userId);  // Updated method to get user details by user_id
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("ProfileFragment", "Column Count: " + cursor.getColumnCount());

            try {
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_LASTNAME));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_USERNAME));

                // Set user details to TextViews
                nameTextView.setText(username);
                firstNameTextView.setText(firstName);
                lastNameTextView.setText(lastName);
            } catch (Exception e) {
                Log.e("ProfileFragment", "Error reading cursor data", e);
            } finally {
                cursor.close();
            }
        } else {
            nameTextView.setText("User Not Found");
            firstNameTextView.setText("");
            lastNameTextView.setText("");
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Clear login state in SharedPreferences
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false); // Set the login flag to false
                    editor.remove("user_id"); // Remove user_id as well
                    editor.apply(); // Save the changes

                    // Redirect to Login Activity
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    // Optional: Close the parent activity if needed
                    getActivity().finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
