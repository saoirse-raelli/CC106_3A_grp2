package com.example.prepmate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class NightModeTheme extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    boolean isNightMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        isNightMode =sharedPreferences.getBoolean("nightmode", false);

        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
