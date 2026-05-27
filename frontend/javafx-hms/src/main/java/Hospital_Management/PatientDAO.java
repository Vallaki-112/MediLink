package Hospital_Management;

import java.sql.*;
import java.util.ArrayList;

public class PatientDAO {
    public void addPatient(AllotBed patient)
            throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO patient " +
                "(patientid,patientname,noofdays,bedno,roomno,floorno) " +
                "VALUES (?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, patient.getPatientId());
        ps.setString(2, patient.getPatientName());
        ps.setInt(3, patient.getNoOfDays());
        ps.setInt(4, patient.getBedNo());
        ps.setInt(5, patient.getRoomNo());
        ps.setInt(6, patient.getFloorNo());

        ps.executeUpdate();

        con.close();
    }

    public ArrayList<AllotBed> getAllPatients()
            throws Exception {

        ArrayList<AllotBed> patients = new ArrayList<>();

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM patient";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            patients.add(

                    new AllotBed(

                            rs.getInt("patientid"),
                            rs.getString("patientname"),
                            rs.getInt("noofdays"),
                            rs.getInt("bedno"),
                            rs.getInt("roomno"),
                            rs.getInt("floorno")));
        }

        con.close();

        return patients;
    }
// DUPILCATE PATIENT ID
    public boolean patientExists(
            int patientId)
            throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM patient " +
                "WHERE patientid=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, patientId);

        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();

        con.close();

        return exists;
    }
// DELETE PATIENT
    public boolean deletePatient(
            int patientId)
            throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM patient " +
                "WHERE patientid=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, patientId);

        int rows = ps.executeUpdate();

        con.close();

        return rows > 0;
    }

    public boolean vacateBed(
            int patientId)
            throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = """
                UPDATE patient
                SET bedno=0,
                    roomno=0,
                    floorno=0
                WHERE patientid=?
                """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, patientId);

        int rows = ps.executeUpdate();

        con.close();

        return rows > 0;
    }
        //=======================
        // IS BED OCCUPIED? 
        //==================
public boolean isBedOccupied(
        int roomNo,
        int bedNo)
        throws Exception {

    Connection con =
            DBConnection.getConnection();

    String sql =
            """
            SELECT *
            FROM patient
            WHERE roomno=?
            AND bedno=?
            """;

    PreparedStatement ps =
            con.prepareStatement(sql);

    ps.setInt(1, roomNo);
    ps.setInt(2, bedNo);

    ResultSet rs =
            ps.executeQuery();

    boolean occupied =
            rs.next();

    con.close();

    return occupied;
}
        //==================================
        //UPDATE PATIENT
        //=====================================
    public boolean updatePatient(AllotBed patient)
            throws Exception {

        Connection con = DBConnection.getConnection();

        String sql = """
                UPDATE patient
                SET patientname=?,
                    noofdays=?,
                    bedno=?,
                    roomno=?,
                    floorno=?
                WHERE patientid=?
                """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1,
                patient.getPatientName());

        ps.setInt(2,
                patient.getNoOfDays());

        ps.setInt(3,
                patient.getBedNo());

        ps.setInt(4,
                patient.getRoomNo());

        ps.setInt(5,
                patient.getFloorNo());

        ps.setInt(6,
                patient.getPatientId());

        int rowsAffected = ps.executeUpdate();

        con.close();

        return rowsAffected > 0;
    }
}