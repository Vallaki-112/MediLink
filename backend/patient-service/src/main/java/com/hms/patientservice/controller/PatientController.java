package com.hms.patientservice.controller;

import com.hms.patientservice.model.Patient;
import com.hms.patientservice.service.PatientService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api") // Keeps our gateway proxy rules completely uniform
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patients/{id}")
    public Patient getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id);
    }

    @PostMapping("/patients")
    public String addPatient(@Valid @RequestBody Patient patient) {
        patientService.addPatient(patient);
        return "Patient Added Successfully";
    }

    @PutMapping("/patients/{id}")
    public String updatePatient(@PathVariable int id, @Valid @RequestBody Patient patient) {
        int rowsAffected = patientService.updatePatient(id, patient);
        if (rowsAffected > 0) {
            return "Patient Updated Successfully";
        }
        return "Patient Not Found";
    }

    @DeleteMapping("/patients/{id}")
    public String deletePatient(@PathVariable int id) {
        int rowsAffected = patientService.deletePatient(id);
        if (rowsAffected > 0) {
            return "Patient Deleted Successfully";
        }
        return "Patient Not Found";
    }

}
