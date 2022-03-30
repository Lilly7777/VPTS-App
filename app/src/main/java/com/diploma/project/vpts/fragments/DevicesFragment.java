package com.diploma.project.vpts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.diploma.project.vpts.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class DevicesFragment extends AppCompatActivity {

    private ExtendedFloatingActionButton addButton;

    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_devices, container, false);
//        addButton = view.findViewById(R.id.add_device);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInputDialog();
            }
        });
    }

    public void openInputDialog(){
        AddDeviceDialog addDeviceDialog = new AddDeviceDialog();
        addDeviceDialog.show(getSupportFragmentManager(), "Add device");
    }

}