package com.example.prepmate.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.prepmate.NotificationMainActivity;
import com.example.prepmate.R;
import com.example.prepmate.calendar.CalendarActivity;
import com.example.prepmate.home.breakfastrecipes.BreakfastRecipesActivity;
import com.example.prepmate.home.dinnerrecipes.DinnerRecipesActivity;
import com.example.prepmate.home.lunchrecipes.LunchRecipesActivity;
import com.example.prepmate.home.midnightsnacksrecipes.MidnightRecipesActivity;
import com.example.prepmate.home.snacksrecipes.SnacksRecipesActivity;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ImageButton btnCustom, btnBreakfast, btnLunch, btnSnack, btnDinner, btnMidSnack, btnNotifications;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize buttons

        btnBreakfast = view.findViewById(R.id.btnBreakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBreakfastRecipesActivity();
            }
        });

        btnLunch = view.findViewById(R.id.btnLunch);
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLunchRecipesActivity();
            }
        });

        btnDinner = view.findViewById(R.id.btnDinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDinnerRecipesActivity();
            }
        });

        btnMidSnack = view.findViewById(R.id.btnMidSnack);
        btnMidSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMidnightRecipesActivity();
            }
        });

        btnSnack = view.findViewById(R.id.btnSnack);
        btnSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSnacksRecipesActivity();
            }
        });


        // Initialize btnNotifications
        btnNotifications = view.findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationMainActivity();
            }
        });







        return view;

    }
//INTENT FOR BUTTONS

    //1
    private void openBreakfastRecipesActivity() {
        if (getActivity() != null) {  // Add null check for safety
            Intent intent = new Intent(getActivity(), BreakfastRecipesActivity.class);
            startActivity(intent);
        }
    }
    //2
    private void openLunchRecipesActivity() {
        if (getActivity() != null) {  // Add null check for safety
            Intent intent = new Intent(getActivity(), LunchRecipesActivity.class);
            startActivity(intent);
        }
    }
    //3
    private void openDinnerRecipesActivity() {
        if (getActivity() != null) {  // Add null check for safety
            Intent intent = new Intent(getActivity(), DinnerRecipesActivity.class);
            startActivity(intent);
        }
    }
    //4
    private void openMidnightRecipesActivity() {
        if (getActivity() != null) {  // Add null check for safety
            Intent intent = new Intent(getActivity(), MidnightRecipesActivity.class);
            startActivity(intent);
        }
    }
    //5
    private void openSnacksRecipesActivity() {
        if (getActivity() != null) {  // Add null check for safety
            Intent intent = new Intent(getActivity(), SnacksRecipesActivity.class);
            startActivity(intent);
        }
    }

//Notification
    private void openNotificationMainActivity() {
        if (getActivity() != null) {  // Add null check for safety
            Intent intent = new Intent(getActivity(), CalendarActivity.class);
            intent.putExtra("reset", true);
            Log.d("SendIntent", "Sending reset = true");
            startActivity(intent);
        }
    }
}