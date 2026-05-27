package Hospital_Management;

// ======================================================
// CLASS: AllotBed
// Inheritance + Method Overriding
// ======================================================
public class AllotBed extends Patient {
    private int bedNo;
    private int roomNo;
    private int floorNo;

    public AllotBed() {
    }

    public AllotBed(int patientId, String patientName, int noOfDays, int bedNo, int roomNo, int floorNo) {
        super(patientId, patientName, noOfDays);
        this.bedNo = bedNo;
        this.roomNo = roomNo;
        this.floorNo = floorNo;
    }

    // Method Overriding
    @Override
    public double calculateFee() {
        double chargePerDay = 2000.0;
        return noOfDays * chargePerDay;
    }

    // Method Overriding
    @Override
    public void displayPatientInformation() {
        super.displayPatientInformation();
        System.out.println("Floor No        : " + floorNo);
        System.out.println("Room No         : " + roomNo);
        System.out.println("Bed No          : " + bedNo);
        System.out.println("Total Fee       : Rs. " + calculateFee());
    }

    public int getPatientId() {
        return patientId;
    }

    // Vacate Bed
    public void vacateBed() {
        bedNo = 0;
        roomNo = 0;
        floorNo = 0;
    }

    // Getter methods
    public String getPatientName() {
        return patientName;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public int getBedNo() {
        return bedNo;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public double getFee() {
        return calculateFee();
    }

    // setters
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public void setBedNo(int bedNo) {
        this.bedNo = bedNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }
}
