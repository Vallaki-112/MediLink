package com.hms.allotmentservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public class AllotBed {

    
    @NotNull(message = "Patient ID cannot be null")
    private int patientId;
    
    private String patientName; // Retained from your original contract
    
    @Min(value = 0, message = "Days cannot be negative")
    private int noOfDays;       // Retained from your original contract
    
    private int bedNo;
    private int roomNo;
    private int floorNo;
    private int allotmentId; // Used for database primary key tracking

    // Default Constructor
    public AllotBed() {}

    public AllotBed(int patientId, String patientName, int noOfDays, int bedNo, int roomNo, int floorNo) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.noOfDays = noOfDays;
        this.bedNo = bedNo;
        this.roomNo = roomNo;
        this.floorNo = floorNo;
    }

    // Your original calculation logic (2000.0 per day)
    public double calculateFee() {
        double chargePerDay = 2000.0;
        return noOfDays * chargePerDay;
    }

    public void vacateBed() {
        this.bedNo = 0;
        this.roomNo = 0;
        this.floorNo = 0;
    }

    // Getters and Setters matching your exact frontend naming structures
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public int getNoOfDays() { return noOfDays; }
    public void setNoOfDays(int noOfDays) { this.noOfDays = noOfDays; }

    public int getBedNo() { return bedNo; }
    public void setBedNo(int bedNo) { this.bedNo = bedNo; }

    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    public int getFloorNo() { return floorNo; }
    public void setFloorNo(int floorNo) { this.floorNo = floorNo; }

    public double getFee() { return calculateFee(); }

    public int getAllotmentId() { return allotmentId; }
    public void setAllotmentId(int allotmentId) { this.allotmentId = allotmentId; }

}
