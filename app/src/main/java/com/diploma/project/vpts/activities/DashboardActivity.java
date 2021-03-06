package com.diploma.project.vpts.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.fragments.DevicesFragment;
import com.diploma.project.vpts.fragments.LocationFragment;
import com.diploma.project.vpts.fragments.ProfileFragment;
import com.diploma.project.vpts.model.VPTSUser;
import com.diploma.project.vpts.service.UserService;
import com.diploma.project.vpts.service.impl.CacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private Retrofit retrofit;
    private UserService userService;
    private CacheManager cacheManager;

    private VPTSUser currentUser;
    private BottomNavigationView bottomNavigationView;

    private final DevicesFragment devicesFragment = new DevicesFragment();
    private final LocationFragment locationFragment = new LocationFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_2);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, locationFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cacheManager = new CacheManager();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            new Thread(() -> {
                Task tokenTask = mAuth.getCurrentUser().getIdToken(false);
                try {
                    Tasks.await(tokenTask);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GetTokenResult tokenResult = (GetTokenResult) Objects.requireNonNull(tokenTask.getResult());
                System.out.println(tokenResult.getToken());
            }).start();
            try {
                currentUser = cacheManager.loadUser(getThisActivity());
            } catch (JsonProcessingException | NullPointerException e) {
                //switchToActivity(LoginActivity.class);
                System.out.println("null");
            }
        } else {
            switchToActivity(LoginActivity.class);
        }
    }

    private Activity getThisActivity() {
        return this;
    }

    private void switchToActivity(Class<?> activity) {
        Intent redirect = new Intent(getApplicationContext(), activity);
        startActivity(redirect);
        this.finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.page_3):
                getSupportFragmentManager().beginTransaction().replace(R.id.container, devicesFragment).commit();
                return true;
            case (R.id.page_2):
                getSupportFragmentManager().beginTransaction().replace(R.id.container, locationFragment).commit();
                return true;
            case (R.id.page_4):
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;
            default:
                System.out.println("Error");
                break;
        }
        return false;
    }
}