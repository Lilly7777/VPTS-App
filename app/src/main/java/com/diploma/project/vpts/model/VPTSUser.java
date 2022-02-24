package com.diploma.project.vpts.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VPTSUser {
    private String userId;

    private String fullName;

    private String phoneNumber;

    private String location;

    public VPTSUser() {
    }

    public VPTSUser(String userId, String fullName, String phoneNumber, String location) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public VPTSUser(String userId, String fullName, String phoneNumber) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
