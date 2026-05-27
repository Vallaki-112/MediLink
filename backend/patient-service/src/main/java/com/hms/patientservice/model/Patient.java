package com.hms.patientservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Patient {

    @Min(
            value = 1,
            message = "Patient ID must be greater than 0")
    private int patientId;

    @NotBlank(
            message = "Patient name cannot be empty")
    private String patientName;

    @Min(
            value = 1,
            message = "Number of days must be greater than 0")
    private int noOfDays;

    @Min(
            value = 1,
            message = "Bed number must be greater than 0")
    private int bedNo;

    @Min(
            value = 1,
            message = "Room number must be greater than 0")
    private int roomNo;

    @Min(
            value = 1,
            message = "Floor number must be greater than 0")
    private int floorNo;

    // existing constructors
    // getters
    // setters
    public Patient() {
    }

    public Patient(
            int patientId,
            String patientName,
            int noOfDays,
            int bedNo,
            int roomNo,
            int floorNo) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.noOfDays = noOfDays;
        this.bedNo = bedNo;
        this.roomNo = roomNo;
        this.floorNo = floorNo;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public int getBedNo() {
        return bedNo;
    }

    public void setBedNo(int bedNo) {
        this.bedNo = bedNo;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

}