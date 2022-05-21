package com.diploma.project.vpts.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.model.Device;
import com.diploma.project.vpts.model.VPTSUser;
import com.diploma.project.vpts.service.AbstractCacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CacheManager implements AbstractCacheManager {

    @Override
    public void saveUser(Activity activity, VPTSUser user) throws JsonProcessingException {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(activity.getString(R.string.saved_user), new ObjectMapper().writeValueAsString(user));
        editor.apply();
    }

    @Override
    public VPTSUser loadUser(Activity activity) throws JsonProcessingException, NullPointerException {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        String savedUser = sharedPreferences.getString(activity.getString(R.string.saved_user), null);
        if (Objects.nonNull(savedUser)) {
            return new ObjectMapper().readValue(savedUser, VPTSUser.class);
        }
        throw new NullPointerException("User doesn't exist in cache");
    }

    @Override
    public void saveDevice(Activity activity, Device newDevice) throws JsonProcessingException {
        List<Device> devices;
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        try{
            devices = loadDevices(activity);
        }catch (NullPointerException e){
            devices = new LinkedList<>();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        devices.add(newDevice);
        editor.putString(activity.getString(R.string.user_devices), new ObjectMapper().writeValueAsString(devices));
        editor.apply();

    }

    @Override
    public List<Device> loadDevices(Activity activity) throws JsonProcessingException, NullPointerException {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        String savedDevices = sharedPreferences.getString(activity.getString(R.string.user_devices), null);
        if(Objects.nonNull(savedDevices)){
            return new ObjectMapper().readValue(savedDevices, new TypeReference<List<Device>>() {});
        }
        throw new NullPointerException("Devices list doesn't exist in cache");
    }

    @Override
    public void clearCache(Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}
