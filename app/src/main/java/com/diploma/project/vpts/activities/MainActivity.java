package com.diploma.project.vpts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.diploma.project.vpts.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }
}