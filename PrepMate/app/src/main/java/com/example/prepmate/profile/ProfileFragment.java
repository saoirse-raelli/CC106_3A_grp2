package com.example.prepmate.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.prepmate.DatabaseHelper;
import com.example.prepmate.LoginActivity;
import com.example.prepmate.R;

public class ProfileFragment extends Fragment {

    private TextView nameTextView, firstNameTextView, lastNameTextView;
    private Button logoutButton;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        nameTextView = view.findViewById(R.id.nameTextView);
        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        logoutButton = view.findViewById(R.id.btnLogout);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(getContext());

        // Set background and text colors based on the current theme
        // applyTheme(view); // Dark mode functionality removed for now

        // Retrieve and display user details
        String username = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                .getString("username", null);
        if (username != null) {
            displayUserDetails(username);
        } else {
            nameTextView.setText("User Not Found");
            firstNameTextView.setText("");
            lastNameTextView.setText("");
        }

        // Set up the logout button click listener
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());

        return view;
    }

    // Removed the applyTheme method for dark mode as it's not needed now
    /*
    private void applyTheme(View view) {
        // Set background and text colors based on the theme
        if ((getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK) ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode settings
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkBackground));
            nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.darkText));
            firstNameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.darkText));
            lastNameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.darkText));
        } else {
            // Light mode settings
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightBackground));
            nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.lightText));
            firstNameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.lightText));
            lastNameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.lightText));
        }
    }
    */

    private void displayUserDetails(String username) {
        Cursor cursor = databaseHelper.getUserDetails(username);
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("ProfileFragment", "Column Count: " + cursor.getColumnCount());

            try {
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_USERNAME));
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_LASTNAME));

                // Set user details to TextViews
                nameTextView.setText(userName);
                firstNameTextView.setText("First Name: " + firstName);
                lastNameTextView.setText("Last Name: " + lastName);
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
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false); // Set the login flag to false
                    editor.remove("username"); // Optionally remove username
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
