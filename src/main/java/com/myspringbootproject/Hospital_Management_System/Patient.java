package com.myspringbootproject.Hospital_Management_System;

public class Patient {

    private int patientId;
    private String patientName;
    private int noOfDays;
    private int bedNo;
    private int roomNo;
    private int floorNo;

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