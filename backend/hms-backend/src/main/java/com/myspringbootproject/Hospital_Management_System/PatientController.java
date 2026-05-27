package com.hms.patientservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;

import java.util.List;

@RestController
public class PatientController {
    private final PatientService patientService;

    public PatientController(
            PatientService patientService) {

        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patients/{id}")
    public Patient getPatientById(
            @PathVariable int id) {

        return patientService.getPatientById(id);
    }

    @PostMapping("/patients")
    public String addPatient(

            @Valid @RequestBody Patient patient) {

        patientService.addPatient(patient);

        return "Patient Added Successfully";
    }

    @PutMapping("/patients/{id}")
    public String updatePatient(

            @PathVariable int id,

            @Valid @RequestBody Patient patient) {

        int rowsAffected = patientService.updatePatient(
                id,
                patient);

        if (rowsAffected > 0) {

            return "Patient Updated Successfully";
        }

        return "Patient Not Found";
    }

    @DeleteMapping("/patients/{id}")
    public String deletePatient(
            @PathVariable int id) {

        int rowsAffected = patientService.deletePatient(id);

        if (rowsAffected > 0) {

            return "Patient Deleted Successfully";
        }

        return "Patient Not Found";
    }
}