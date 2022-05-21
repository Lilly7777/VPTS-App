package com.diploma.project.vpts.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.activities.LoginActivity;
import com.diploma.project.vpts.service.impl.CacheManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    Button logoutButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = view.findViewById(R.id.logoutButton);
        TextView email = view.findViewById(R.id.user_email);
        email.setText(email.getText().toString().concat(" ".concat(mAuth.getCurrentUser().getEmail())));

        logoutButton.setOnClickListener(view1 -> {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            new CacheManager().clearCache(getActivity());
            Intent redirect = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(redirect);
            getActivity().getFragmentManager().popBackStack();
        });

        return view;
    }
}