package Hospital_Management;

//==================================================================
//Declerations and import
//==================================================================
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

//==================================================================

public class HospitalController {
        // ======================================================
        // FXML CONTROLS (Connected from hospital.fxml)
        // ======================================================

        @FXML
        private TextField txtPatientId;

        @FXML
        private TextField txtPatientName;

        @FXML
        private TextField txtNoOfDays;

        @FXML
        private TextField txtBedNo;

        @FXML
        private TextField txtRoomNo;

        @FXML
        private TextField txtFloorNo;

        @FXML
        private TextField txtSearchId;

        @FXML
        private TableView<AllotBed> tablePatients;

        @FXML
        private TableColumn<AllotBed, Integer> colPatientId;

        @FXML
        private TableColumn<AllotBed, String> colPatientName;

        @FXML
        private TableColumn<AllotBed, Integer> colNoOfDays;

        @FXML
        private TableColumn<AllotBed, Integer> colBedNo;

        @FXML
        private TableColumn<AllotBed, Integer> colRoomNo;

        @FXML
        private TableColumn<AllotBed, Integer> colFloorNo;

        @FXML
        private TableColumn<AllotBed, Double> colFee;

        // ======================================================
        // BACKEND OBJECTS
        // ======================================================

        // review this
        // Uses your existing HospitalManagement class
        private HospitalManagement hospital = new HospitalManagement();
        private PatientDAO patientDAO = new PatientDAO();
        // Observable list for JavaFX TableView
        private ObservableList<AllotBed> observablePatients = FXCollections.observableArrayList();

        // ======================================================
        // INITIALIZE METHOD
        // Automatically called after FXML is loaded
        // ======================================================

        @FXML
        public void initialize() {
                // temporary code
                try {

                        String response = APIClient.get("/patients");

                        System.out.println(response);

                } catch (Exception e) {

                        e.printStackTrace();
                }

                // Connect table columns with getter methods in AllotBed
                colPatientId.setCellValueFactory(
                                new PropertyValueFactory<>("patientId"));

                colPatientName.setCellValueFactory(
                                new PropertyValueFactory<>("patientName"));

                colNoOfDays.setCellValueFactory(
                                new PropertyValueFactory<>("noOfDays"));

                colBedNo.setCellValueFactory(
                                new PropertyValueFactory<>("bedNo"));

                colRoomNo.setCellValueFactory(
                                new PropertyValueFactory<>("roomNo"));

                colFloorNo.setCellValueFactory(
                                new PropertyValueFactory<>("floorNo"));

                colFee.setCellValueFactory(
                                new PropertyValueFactory<>("fee"));
                // Set data source for TableView
                tablePatients.setItems(observablePatients);

                loadPatientsFromDatabase();
        }

        // ======================================================
        // Methode Load Patients From render
        // ======================================================
        private void loadPatientsFromDatabase() {

                try {

                        String json = APIClient.get(
                                        "/patients");

                        ObjectMapper mapper = new ObjectMapper();

                        List<AllotBed> patients = mapper.readValue(
                                        json,
                                        new TypeReference<List<AllotBed>>() {
                                        });

                        observablePatients.clear();

                        observablePatients.addAll(
                                        patients);

                        tablePatients.refresh();

                } catch (Exception e) {

                        e.printStackTrace();

                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "API Error",
                                        e.getMessage());
                }
        }

        // ======================================================
        // ADD PATIENT BUTTON
        // ======================================================

        @FXML
        private void addPatient() {
                try {
                        // Read values from text fields

                        int patientId = Integer.parseInt(txtPatientId.getText());
                        // =========================================
                        // VALIDATION CHECK: DUPLICATE PATIENT ID
                        // =========================================
                        if (patientDAO.patientExists(
                                        patientId)) {

                                showAlert(
                                                Alert.AlertType.ERROR,
                                                "Duplicate ID",
                                                "Patient ID already exists.");

                                return;
                        }
                        String patientName = txtPatientName.getText();
                        // =========================================
                        // VALIDATION CHECK: EMPTY PATIENT NAME
                        // =========================================
                        if (patientName.trim().isEmpty()) {

                                showAlert(
                                                Alert.AlertType.ERROR,
                                                "Invalid Input",
                                                "Patient name cannot be empty.");

                                return;
                        }
                        int noOfDays = Integer.parseInt(txtNoOfDays.getText());
                        int bedNo = Integer.parseInt(txtBedNo.getText());
                        int roomNo = Integer.parseInt(txtRoomNo.getText());
                        // =========================================
                        // CONDITION TO CHECK BED OCCUPANCY
                        // =========================================
                        if (patientDAO.isBedOccupied(
                                        roomNo,
                                        bedNo)) {

                                showAlert(
                                                Alert.AlertType.ERROR,
                                                "Bed Occupied",
                                                "This bed is already occupied.");

                                return;
                        }
                        int floorNo = Integer.parseInt(txtFloorNo.getText());

                        // =========================================
                        // VALIDATION CHECK: NEGATIVE VALUES
                        // =========================================

                        if (patientId <= 0 ||
                                        noOfDays <= 0 ||
                                        bedNo < 0 ||
                                        roomNo < 0 ||
                                        floorNo < 0) {

                                showAlert(
                                                Alert.AlertType.ERROR,
                                                "Invalid Input",
                                                "Values cannot be negative.");

                                return;
                        }
                        // Create object using your existing constructor
                        AllotBed patient = new AllotBed(
                                        patientId,
                                        patientName,
                                        noOfDays,
                                        bedNo,
                                        roomNo,
                                        floorNo);

                        patientDAO.addPatient(patient);

                        // Add to backend
                        hospital.addPatient(patient);

                        // Add to TableView
                        observablePatients.add(patient);

                        // Clear input fields
                        clearFields();

                        // Success message
                        showAlert(
                                        Alert.AlertType.INFORMATION,
                                        "Success",
                                        "Patient added successfully.");

                } catch (NumberFormatException e) {
                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Invalid Input",
                                        "Please enter valid numeric values.");
                } catch (Exception e) {
                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Error",
                                        e.getMessage());
                }
        }

        // ======================================================
        // UPDATE PATIENT BUTTON
        // ======================================================

        @FXML
        private void updatePatient() {

                try {

                        int patientId = Integer.parseInt(
                                        txtPatientId.getText());

                        String patientName = txtPatientName.getText();
                        // =========================================
                        // VALIDATION CHECK: EMPTY PATIENT NAME
                        // =========================================
                        if (patientName.trim().isEmpty()) {

                                showAlert(
                                                Alert.AlertType.ERROR,
                                                "Invalid Input",
                                                "Patient name cannot be empty.");

                                return;
                        }

                        int noOfDays = Integer.parseInt(
                                        txtNoOfDays.getText());

                        int bedNo = Integer.parseInt(
                                        txtBedNo.getText());

                        int roomNo = Integer.parseInt(
                                        txtRoomNo.getText());

                        int floorNo = Integer.parseInt(
                                        txtFloorNo.getText());

                        // =========================================
                        // VALIDATION CHECK: NEGATIVE VALUES
                        // =========================================

                        if (patientId <= 0 ||
                                        noOfDays <= 0 ||
                                        bedNo < 0 ||
                                        roomNo < 0 ||
                                        floorNo < 0) {

                                showAlert(
                                                Alert.AlertType.ERROR,
                                                "Invalid Input",
                                                "Values cannot be negative.");

                                return;
                        }
                        AllotBed patient = new AllotBed(
                                        patientId,
                                        patientName,
                                        noOfDays,
                                        bedNo,
                                        roomNo,
                                        floorNo);

                        boolean updated = patientDAO.updatePatient(
                                        patient);

                        if (updated) {

                                loadPatientsFromDatabase();

                                clearFields();

                                showAlert(
                                                Alert.AlertType.INFORMATION,
                                                "Success",
                                                "Patient updated successfully.");

                        } else {

                                showAlert(
                                                Alert.AlertType.WARNING,
                                                "Not Found",
                                                "Patient ID does not exist.");
                        }

                } catch (NumberFormatException e) {

                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Invalid Input",
                                        "Please enter valid numeric values.");

                } catch (Exception e) {

                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Database Error",
                                        e.getMessage());
                }
        }
        // ======================================================
        // REMOVE PATIENT BUTTON
        // ======================================================

        @FXML
        private void removePatient() {
                try {
                        int patientId = Integer.parseInt(txtSearchId.getText());

                        boolean rowsAffected = patientDAO.deletePatient(patientId);

                        if (rowsAffected) {

                                hospital.removePatient(patientId);

                                observablePatients.removeIf(
                                                patient -> patient.getPatientId() == patientId);

                                showAlert(
                                                Alert.AlertType.INFORMATION,
                                                "Success",
                                                "Patient deleted successfully.");

                        } else {

                                showAlert(
                                                Alert.AlertType.WARNING,
                                                "Not Found",
                                                "Patient ID does not exist.");

                        }

                } catch (NumberFormatException e) {
                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Invalid Input",
                                        "Enter a valid Patient ID.");
                } catch (Exception e) {

                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Database Error",
                                        e.getMessage());
                }
        }

        // ======================================================
        // VACATE BED BUTTON
        // ======================================================

        @FXML
        private void vacateBed() {
                try {
                        int patientId = Integer.parseInt(txtSearchId.getText());

                        boolean rowsAffected = patientDAO.vacateBed(
                                        patientId);
                        ;

                        if (rowsAffected) {

                                hospital.removeBed(patientId);

                                for (AllotBed patient : observablePatients) {

                                        if (patient.getPatientId() == patientId) {

                                                patient.vacateBed();

                                                break;
                                        }
                                }

                                tablePatients.refresh();

                                showAlert(
                                                Alert.AlertType.INFORMATION,
                                                "Success",
                                                "Bed vacated successfully.");
                        } else {

                                showAlert(
                                                Alert.AlertType.WARNING,
                                                "Not Found",
                                                "Patient ID not found.");
                        }

                } catch (NumberFormatException e) {

                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Invalid Input",
                                        "Enter a valid Patient ID.");
                } catch (Exception e) {

                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Database Error",
                                        e.getMessage());
                }

        }

        // ======================================================
        // SEARCH PATIENT BUTTON
        // ======================================================

        @FXML
        private void searchPatient() {
                try {
                        int patientId = Integer.parseInt(txtSearchId.getText());

                        for (AllotBed patient : observablePatients) {
                                if (patient.getPatientId() == patientId) {
                                        tablePatients.getSelectionModel().select(patient);
                                        tablePatients.scrollTo(patient);

                                        showAlert(
                                                        Alert.AlertType.INFORMATION,
                                                        "Patient Found",
                                                        "Patient: " + patient.getPatientName());
                                        return;
                                }
                        }

                        showAlert(
                                        Alert.AlertType.WARNING,
                                        "Not Found",
                                        "Patient not found.");

                } catch (NumberFormatException e) {
                        showAlert(
                                        Alert.AlertType.ERROR,
                                        "Invalid Input",
                                        "Enter a valid Patient ID.");
                }
        }

        // ======================================================
        // DISPLAY ALL PATIENTS BUTTON
        // ======================================================

        @FXML
        private void displayAllPatients() {

                loadPatientsFromDatabase();
        }

        // ======================================================
        // CLEAR INPUT FIELDS
        // ======================================================

        private void clearFields() {
                txtPatientId.clear();
                txtPatientName.clear();
                txtNoOfDays.clear();
                txtBedNo.clear();
                txtRoomNo.clear();
                txtFloorNo.clear();
        }

        // ======================================================
        // SHOW ALERT DIALOG
        // ======================================================

        private void showAlert(
                        Alert.AlertType type,
                        String title,
                        String message) {

                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }
}
