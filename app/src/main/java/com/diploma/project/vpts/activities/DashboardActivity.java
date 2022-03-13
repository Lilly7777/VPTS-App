package com.diploma.project.vpts.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.service.UserService;
import com.diploma.project.vpts.service.impl.CacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Retrofit retrofit;
    private UserService userService;
    private CacheManager cacheManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        NavigationBarView.OnItemSelectedListener { item ->
//            TODO:
//        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            try {
                cacheManager.loadUser(getThisActivity());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    private Activity getThisActivity() {
        return this;
    }
}