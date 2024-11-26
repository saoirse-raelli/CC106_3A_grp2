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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SwitchCompat switchCompat = view.findViewById(R.id.switchCompat);
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        editor = sharedPreferences.edit();

        isNightMode =sharedPreferences.getBoolean("nightmode", false);

        if (isNightMode) {
            switchCompat.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switchCompat.setOnClickListener(v ->{
            nyThemes();
        });

        nameTextView = view.findViewById(R.id.nameTextView);
        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        logoutButton = view.findViewById(R.id.btnLogout);
        btnAbout = view.findViewById(R.id.btnAbout);

        databaseHelper = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId != -1) {
            displayUserDetails(userId);
        } else {
            nameTextView.setText("User Not Found");
            firstNameTextView.setText("");
            lastNameTextView.setText("");
        }

        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutPrepMate.class);
            startActivity(intent);
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
        Cursor cursor = databaseHelper.getUserDetailsById(userId);
        if (cursor != null && cursor.moveToFirst()) {
            Log.d("ProfileFragment", "Column Count: " + cursor.getColumnCount());

            try {
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_LASTNAME));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_USERNAME));

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
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.remove("user_id");
                    editor.apply();

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    getActivity().finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
