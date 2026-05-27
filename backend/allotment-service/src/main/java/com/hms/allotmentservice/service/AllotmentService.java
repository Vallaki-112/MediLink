package com.hms.allotmentservice.service;

import com.hms.allotmentservice.model.AllotBed;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class AllotmentService {

    private final JdbcTemplate jdbcTemplate;

    public AllotmentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AllotBed> allotmentRowMapper = new RowMapper<AllotBed>() {
        @Override
        public AllotBed mapRow(@org.springframework.lang.NonNull java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            AllotBed allotment = new AllotBed();
            allotment.setAllotmentId(rs.getInt("allotment_id"));
            allotment.setPatientId(rs.getInt("patient_id"));
            allotment.setPatientName(rs.getString("patient_name"));
            allotment.setNoOfDays(rs.getInt("no_of_days"));
            allotment.setFloorNo(rs.getInt("floor_no"));
            allotment.setRoomNo(rs.getInt("room_no"));
            allotment.setBedNo(rs.getInt("bed_no"));
            return allotment;
        }
    };

    public List<AllotBed> getAllAllotments() {
        String sql = "SELECT * FROM bed_allotments";
        return jdbcTemplate.query(sql, allotmentRowMapper);
    }

    public void allotBed(AllotBed allotment) {
        String sql = "INSERT INTO bed_allotments (patient_id, patient_name, no_of_days, floor_no, room_no, bed_no, total_fee) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            allotment.getPatientId(), 
            allotment.getPatientName(), 
            allotment.getNoOfDays(), 
            allotment.getFloorNo(), 
            allotment.getRoomNo(), 
            allotment.getBedNo(), 
            allotment.calculateFee() // Automatically passes your updated 2000.0 calculation rule!
        );
    }

    public int vacateBed(int id) {
        String sql = "DELETE FROM bed_allotments WHERE allotment_id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
