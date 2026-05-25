package com.myspringbootproject.Hospital_Management_System;

import java.util.List;
import org.springframework.stereotype.Service;
import com.myspringbootproject.Hospital_Management_System.PatientDAO;
import com.myspringbootproject.Hospital_Management_System.Patient;

@Service
public class PatientService {

    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public Patient getPatientById(int id) {
        return patientDAO.getPatientById(id);
    }

    public int addPatient(Patient patient) {
        return patientDAO.addPatient(patient);
    }

    public int updatePatient(int id, Patient patient) {
        return patientDAO.updatePatient(id, patient);
    }

    public int deletePatient(int id) {
        return patientDAO.deletePatient(id);
    }

}
