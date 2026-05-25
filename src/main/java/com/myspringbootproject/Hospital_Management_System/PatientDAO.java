package com.myspringbootproject.Hospital_Management_System;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDAO {

    private final JdbcTemplate jdbcTemplate;

    public PatientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Patient> getAllPatients() {

        String sql = "SELECT * FROM patient";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Patient(
                        rs.getInt("patientid"),
                        rs.getString("patientname"),
                        rs.getInt("noofdays"),
                        rs.getInt("bedno"),
                        rs.getInt("roomno"),
                        rs.getInt("floorno")));
    }

    public int addPatient(Patient patient) {

        String sql = """
                INSERT INTO patient
                (patientid, patientname,
                 noofdays, bedno,
                 roomno, floorno)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                patient.getPatientId(),
                patient.getPatientName(),
                patient.getNoOfDays(),
                patient.getBedNo(),
                patient.getRoomNo(),
                patient.getFloorNo());
    }

    public int updatePatient(
            int patientId,
            Patient patient) {

        String sql = """
                UPDATE patient
                SET patientname=?,
                    noofdays=?,
                    bedno=?,
                    roomno=?,
                    floorno=?
                WHERE patientid=?
                """;

        return jdbcTemplate.update(
                sql,

                patient.getPatientName(),
                patient.getNoOfDays(),
                patient.getBedNo(),
                patient.getRoomNo(),
                patient.getFloorNo(),

                patientId);
    }

    public int deletePatient(int patientId) {

        String sql = "DELETE FROM patient WHERE patientid=?";

        return jdbcTemplate.update(
                sql,
                patientId);
    }

    public Patient getPatientById(int patientId) {

        String sql = "SELECT * FROM patient WHERE patientid=?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Patient(
                        rs.getInt("patientid"),
                        rs.getString("patientname"),
                        rs.getInt("noofdays"),
                        rs.getInt("bedno"),
                        rs.getInt("roomno"),
                        rs.getInt("floorno")),
                patientId);
    }
}
