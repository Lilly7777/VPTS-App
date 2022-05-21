package com.diploma.project.vpts.service;

import android.app.Activity;

import com.diploma.project.vpts.model.Device;
import com.diploma.project.vpts.model.VPTSUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AbstractCacheManager {
    void saveUser(Activity activity, VPTSUser user) throws JsonProcessingException;
    VPTSUser loadUser(Activity activity) throws JsonProcessingException, NullPointerException;

    void saveDevice(Activity activity, Device newDevice) throws JsonProcessingException;
    List<Device> loadDevices(Activity activity) throws JsonProcessingException, NullPointerException;

    void clearCache(Activity activity);
}
