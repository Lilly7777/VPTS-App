package com.diploma.project.vpts.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class VPTSUser {

    private String userId;

    private String fullName;

    private String phoneNumber;

    private String location;

    private Date dateRegistered;

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

    @JsonProperty("_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("date_registered")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public Date getDateRegistered() {
        return dateRegistered;
    }

    @JsonProperty("date_registered")
    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
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
