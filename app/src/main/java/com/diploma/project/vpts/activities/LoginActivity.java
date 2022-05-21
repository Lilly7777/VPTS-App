package com.diploma.project.vpts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.model.Device;
import com.diploma.project.vpts.model.VPTSUser;
import com.diploma.project.vpts.service.DevicesService;
import com.diploma.project.vpts.service.UserService;
import com.diploma.project.vpts.service.impl.CacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Retrofit retrofit;
    private UserService userService;
    private DevicesService devicesService;
    private CacheManager cacheManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        mAuth = FirebaseAuth.getInstance();
        initializeHttpClientService("http://192.168.1.2:80");
        cacheManager = new CacheManager();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextInputLayout emailInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField7);
        TextInputEditText emailInput = (TextInputEditText) findViewById(R.id.emailInput2);
        TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField8);
        TextInputEditText passwordInput = (TextInputEditText) findViewById(R.id.passwordInput2);
        Button signInButton = (Button) findViewById(R.id.containedButton2);
        Button switchToRegisterButton = (Button) findViewById(R.id.switchToRegisterButton);

        signInButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(emailInput.getText()).toString().trim().toLowerCase();
            String password = Objects.requireNonNull(passwordInput.getText()).toString().trim().toLowerCase();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(event -> {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        Call<VPTSUser> result = userService.getUser("Bearer " + currentUser.getIdToken(false)
                                .getResult().getToken(), currentUser.getUid());

                        result.enqueue(new Callback<VPTSUser>() {
                            @Override
                            public void onResponse(Call<VPTSUser> call, Response<VPTSUser> response) {
                                if (response.code() == 200) {
                                    try {
                                        cacheManager.saveUser(getThisActivity(), response.body());
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<VPTSUser> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                        Call<List<Device>> devicesResult = devicesService.getAllDevicesByUserId("Bearer " + currentUser.getIdToken(false)
                                        .getResult().getToken(), mAuth.getCurrentUser().getUid());
                        devicesResult.enqueue(new Callback<List<Device>>() {
                            @Override
                            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                                if (response.code() == 200) {
                                    response.body().forEach(device -> {
                                        try {
                                            cacheManager.saveDevice(getThisActivity(), device);
                                        } catch (JsonProcessingException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Device>> call, Throwable t) {

                            }
                        });

                        switchToActivity(DashboardActivity.class);

//                        System.out.println(currentUser.getIdToken(false).getResult().getToken());
//
//                        String uuid = currentUser.getUid();
//                        Toast.makeText(this, currentUser.getIdToken(false).getResult().getToken(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(event -> {
                        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        });

        switchToRegisterButton.setOnClickListener(view -> {
            switchToActivity(RegisterActivity.class);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            switchToActivity(DashboardActivity.class);
        }
    }

    private void initializeHttpClientService(String serverBaseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(serverBaseUrl)
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .build();
        userService = retrofit.create(UserService.class);
        devicesService = retrofit.create(DevicesService.class);
    }

    private void switchToActivity(Class<?> activity) {
        Intent redirect = new Intent(getApplicationContext(), activity);
        startActivity(redirect);
        finish();
    }

    private Activity getThisActivity() {
        return this;
    }
}