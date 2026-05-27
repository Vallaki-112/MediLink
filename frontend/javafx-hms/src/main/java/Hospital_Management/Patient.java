package Hospital_Management;
// ======================================================
// ABSTRACT CLASS: Patient
// Demonstrates Abstraction
// ======================================================
    public abstract class Patient {
    protected int patientId;
    protected String patientName;
    protected int noOfDays;
public Patient() {
}
    // Constructor
    public Patient(int patientId, String patientName, int noOfDays) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.noOfDays = noOfDays;
    }

    // Abstract Method (Abstraction)
    public abstract double calculateFee();

    // Display basic patient information
    public void displayPatientInformation() {
        System.out.println("Patient ID      : " + patientId);
        System.out.println("Patient Name    : " + patientName);
        System.out.println("Number of Days  : " + noOfDays);
    }
}