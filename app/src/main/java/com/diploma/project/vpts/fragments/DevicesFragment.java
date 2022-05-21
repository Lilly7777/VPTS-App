package com.diploma.project.vpts.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.model.Device;
import com.diploma.project.vpts.service.DevicesService;
import com.diploma.project.vpts.service.impl.CacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class DevicesFragment extends Fragment {

    private ExtendedFloatingActionButton addButton;

    private Retrofit retrofit;
    private DevicesService deviceService;

    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initializeHttpClientService("http://192.168.1.2:80");
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        addButton = view.findViewById(R.id.add_device);
        LinearLayout deviceCard = view.findViewById(R.id.device_card);
        TextView deviceTitle = view.findViewById(R.id.device_title);
        TextView deviceVehicleNumber = view.findViewById(R.id.device_vehicle_number);
        TextView deviceCurrentLoc = view.findViewById(R.id.device_current_location);

        try {
            new CacheManager().loadDevices(getActivity()).forEach(device -> {
                deviceCard.setVisibility(View.VISIBLE);
                deviceTitle.setText(device.getVehicleRegistrationNumber());
                deviceVehicleNumber.setText(device.getCertificateNo());
                deviceCurrentLoc.setText("Current location - 0.00, 0.00");
            });
        } catch (Exception ignored) {
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInputDialog();
            }
        });
        return view;
    }

    public void openInputDialog() {
        AddDeviceDialog addDeviceDialog = new AddDeviceDialog(deviceService, getActivity());
        addDeviceDialog.show(this.getChildFragmentManager(), "we");
        //addDeviceDialog.show(getSupportFragmentManager(), "Add device");
    }

    private void initializeHttpClientService(String serverBaseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(serverBaseUrl)
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .build();
        deviceService = retrofit.create(DevicesService.class);
    }

    public static class AddDeviceDialog extends DialogFragment {

        protected DevicesService devicesService;
        protected Activity callingActivity;

        public AddDeviceDialog() {
        }

        public AddDeviceDialog(DevicesService devicesService, Activity activity) {
            this.devicesService = devicesService;
            this.callingActivity = activity;
        }

        protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
        private CacheManager cacheManager = new CacheManager();

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_device_dialog, null);
            EditText inputVehicleNumber = (EditText) view.findViewById(R.id.vehicleNumber);
            EditText inputDeviceCertificateNumber = (EditText) view.findViewById(R.id.deviceCertificateNumber);

            Button addDeviceButton = (Button) view.findViewById(R.id.addDeviceDialogButton);
            addDeviceButton.setOnClickListener(view1 -> {
                String vehicleNumber = inputVehicleNumber.getText().toString();
                String deviceCertificateNumber = inputDeviceCertificateNumber.getText().toString();
                if (!vehicleNumber.isEmpty() && !deviceCertificateNumber.isEmpty()) {
                    Call<Device> result = devicesService.registerDevice("Bearer " + mAuth
                            .getCurrentUser().getIdToken(false).getResult().getToken(), new Device(mAuth.getCurrentUser().getUid(),
                            deviceCertificateNumber, vehicleNumber));
                    result.enqueue(new Callback<Device>() {
                        @Override
                        public void onResponse(Call<Device> call, Response<Device> response) {
                            if (response.code() == 201) {
                                try {
                                    cacheManager.saveDevice(callingActivity, response.body());
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            } else if (response.code() == 403) {
                                Toast.makeText(view.getContext(), "Error: This device has been registered by another user.", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 409) {
                                Toast.makeText(view.getContext(), "Error: You have already registered this device.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), "An unknown error occurred.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Device> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(view.getContext(), "Service unavailable", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (getDialog() != null) {
                    getDialog().dismiss();
                }
            });

            return builder
                    .setMessage("ADD DEVICE")
                    .setView(view)
                    //.setPositiveButton(getString(R.string.ok), (dialog, which) -> {} )
                    .create();
        }
    }

}