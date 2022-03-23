package com.diploma.project.vpts.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.diploma.project.vpts.R;
import com.diploma.project.vpts.model.VPTSUser;
import com.diploma.project.vpts.service.AbstractCacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
