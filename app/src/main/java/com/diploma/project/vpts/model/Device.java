package com.diploma.project.vpts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Device {
    private String deviceId;

    private Date dateRegistered = new Date();

    private String userId;

    private String certificateNo;

    private String vehicleRegistrationNumber;

    public Device() {
    }

    public Device(String userId, String certificateNo, String vehicleRegistrationNumber) {
        this.userId = userId;
        this.certificateNo = certificateNo;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    @JsonProperty("_id")
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty("date_registered")
    public Date getDateRegistered() {
        return dateRegistered;
    }

    @JsonProperty("date_registered")
    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("device_certificate_no")
    public String getCertificateNo() {
        return certificateNo;
    }

    @JsonProperty("device_certificate_no")
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    @JsonProperty("vehicle_registration_number")
    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    @JsonProperty("vehicle_registration_number")
    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }
}
