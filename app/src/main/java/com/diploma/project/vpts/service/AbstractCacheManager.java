package com.diploma.project.vpts.service;

import android.app.Activity;

import com.diploma.project.vpts.model.VPTSUser;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AbstractCacheManager {
    void saveUser(Activity activity, VPTSUser user) throws JsonProcessingException;

    VPTSUser loadUser(Activity activity) throws JsonProcessingException, NullPointerException;
}
