package com.hms.frontend.application;

import com.hms.frontend.controller.HospitalManagement;
import com.hms.frontend.model.AllotBed;

// ======================================================
// MAIN CLASS (No User Input)
// ======================================================
public class HospitalManagementSystem {
    public static void main(String[] args) {

        HospitalManagement hospital = new HospitalManagement();

        // Create sample patients
        AllotBed patient1 = new AllotBed(
                101,
                "Riya Gupta",
                5,
                1,
                201,
                2);

        AllotBed patient2 = new AllotBed(
                102,
                "Pankaj Verma",
                3,
                2,
                203,
                2);

        AllotBed patient3 = new AllotBed(
                103,
                "Neha Shah",
                7,
                4,
                202,
                2);

        // addPatient()
        hospital.addPatient(patient1);
        hospital.addPatient(patient2);
        hospital.addPatient(patient3);

        // displayPatient_Information()
        hospital.displayPatientInformation(101);

        // removeBed()
        // hospital.removeBed(101);

        // Display after vacating bed
        // hospital.displayPatientInformation(101);

        // removePatient()
        // hospital.removePatient(102);

        // Display all remaining patients
        System.out.println("\n===== ALL PATIENTS =====");
        hospital.displayAllPatients();
    }
}