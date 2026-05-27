package Hospital_Management;
import java.util.*;
// ======================================================
// CLASS: HospitalManagement
// Uses ArrayList
// ======================================================
public class HospitalManagement {
    private ArrayList<AllotBed> patientList;

    public HospitalManagement() {
        patientList = new ArrayList<>();
    }

    // addPatient()
    public void addPatient(AllotBed patient) {
        patientList.add(patient);
        System.out.println("Patient added successfully.");
    }

    // removePatient()
    public void removePatient(int patientId) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientId() == patientId) {
                patientList.remove(i);
                System.out.println("Patient removed successfully.");
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    // removeBed()
    public void removeBed(int patientId) {
        for (AllotBed patient : patientList) {
            if (patient.getPatientId() == patientId) {
                patient.vacateBed();
                System.out.println("Bed vacated successfully.");
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    // displayPatient_Information()
    public void displayPatientInformation(int patientId) {
        for (AllotBed patient : patientList) {
            if (patient.getPatientId() == patientId) {
                System.out.println("\n===== PATIENT INFORMATION =====");
                patient.displayPatientInformation();
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    // Display all patients
    public void displayAllPatients() {
        for (AllotBed patient : patientList) {
            System.out.println("\n----------------------------");
            patient.displayPatientInformation();
        }
    }

    // Method to Return the Patient List
    public ArrayList<AllotBed> getPatientList() {
        return patientList;
    }

}