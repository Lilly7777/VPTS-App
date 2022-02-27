package com.diploma.project.vpts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diploma.project.vpts.model.VPTSUser;
import com.diploma.project.vpts.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Retrofit retrofit;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mAuth = FirebaseAuth.getInstance();
        initializeHttpClientService("http://192.168.1.2:8080");

        TextInputLayout usernameInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField1);
        TextInputEditText usernameInput = (TextInputEditText) findViewById(R.id.usernameInput);
        TextInputLayout emailInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField2);
        TextInputEditText emailInput = (TextInputEditText) findViewById(R.id.emailInput);
        TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField3);
        TextInputEditText passwordInput = (TextInputEditText) findViewById(R.id.passwordInput);
        TextInputLayout confirmPasswordInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField4);
        TextInputEditText confirmPasswordInput = (TextInputEditText) findViewById(R.id.confirmPasswordInput);
        TextInputLayout phoneNumberInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField5);
        TextInputEditText phoneNumberInput = (TextInputEditText) findViewById(R.id.phoneInput);
        TextInputLayout cityInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField6);
        TextInputEditText cityInput = (TextInputEditText) findViewById(R.id.cityInput);
        Button signUpButton = (Button) findViewById(R.id.containedButton);

        signUpButton.setOnClickListener(view -> {
            String username = Objects.requireNonNull(usernameInput.getText()).toString().trim().toLowerCase();
            String email = Objects.requireNonNull(emailInput.getText()).toString().trim().toLowerCase();
            String password = Objects.requireNonNull(passwordInput.getText()).toString().trim().toLowerCase();
            String confirmPassword = Objects.requireNonNull(confirmPasswordInput.getText()).toString().trim().toLowerCase();
            String phoneNumber = Objects.requireNonNull(phoneNumberInput.getText()).toString().trim().toLowerCase();
            String city = Objects.requireNonNull(cityInput.getText()).toString().trim().toLowerCase();
            passwordInputLayout.setEnabled(false);
            //TODO: Validate data
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(event -> {
                        VPTSUser user = new VPTSUser(
                                mAuth.getCurrentUser().getUid(),
                                username, phoneNumber, city
                        );

                        Call<VPTSUser> result = userService.createUser("Bearer " + mAuth.getCurrentUser().getIdToken(false)
                                .getResult().getToken(), user);

                        result.enqueue(new Callback<VPTSUser>() {
                            @Override
                            public void onResponse(Call<VPTSUser> call, Response<VPTSUser> response) {
                                System.out.println(response.body());
                                System.out.println(response.code());
                                if (response.code() == 201) {
                                    switchToActivity(DashboardActivity.class);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error. Forbidden", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<VPTSUser> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Service unavailable", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .addOnFailureListener(event -> {
                        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initializeHttpClientService(String serverBaseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(serverBaseUrl)
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .build();
        userService = retrofit.create(UserService.class);
    }

    private void switchToActivity(Class<?> activity) {
        Intent redirect = new Intent(getApplicationContext(), activity);
        startActivity(redirect);
        finish();
    }
}