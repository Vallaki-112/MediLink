package com.hms.patientservice.service;

import org.springframework.stereotype.Service;
import com.hms.patientservice.model.Patient;
import com.hms.patientservice.exception.PatientNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final JdbcTemplate jdbcTemplate;

    // Spring automatically injects the DataSource configured in
    // application.properties
    public PatientService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper definition to cleanly map SQL result rows to our Patient object
    private final RowMapper<Patient> patientRowMapper = new RowMapper<Patient>() {
        @Override

        public Patient mapRow(@org.springframework.lang.NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Patient patient = new Patient();
            patient.setPatientId(rs.getInt("patient_id"));
            patient.setPatientName(rs.getString("patient_name"));
            return patient;
        }
    };

    public List<Patient> getAllPatients() {
        String sql = "SELECT * FROM patients";
        return jdbcTemplate.query(sql, patientRowMapper);
    }

    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE patient_id =";
        return jdbcTemplate.query(sql, patientRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " not found."));
    }

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patients (patient_id, patient_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, patient.getPatientId(), patient.getPatientName());
    }

    public int updatePatient(int id, Patient patient) {
        String sql = "UPDATE patients SET patient_name = ? WHERE patient_id = ?";
        return jdbcTemplate.update(sql, patient.getPatientName(), id);
    }

    public int deletePatient(int id) {
        // First verify if the patient exists
        getPatientById(id);

        String sql = "DELETE FROM patients WHERE patient_id = ?";
        return jdbcTemplate.update(sql, id);
    }

}