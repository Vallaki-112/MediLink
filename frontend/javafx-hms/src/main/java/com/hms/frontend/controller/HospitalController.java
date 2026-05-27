package com.hms.frontend.controller;

//==================================================================
// Declarations and Imports
//==================================================================
import com.hms.frontend.model.AllotBed; 
import com.hms.frontend.util.APIClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

//==================================================================

public class HospitalController {
        // ======================================================
        // FXML CONTROLS (Connected from hospital.fxml)
        // ======================================================

        @FXML private TextField txtPatientId;
        @FXML private TextField txtPatientName;
        @FXML private TextField txtNoOfDays;
        @FXML private TextField txtBedNo;
        @FXML private TextField txtRoomNo;
        @FXML private TextField txtFloorNo;
        @FXML private TextField txtSearchId;

        @FXML private TableView<AllotBed> tablePatients;
        @FXML private TableColumn<AllotBed, Integer> colPatientId;
        @FXML private TableColumn<AllotBed, String> colPatientName;
        @FXML private TableColumn<AllotBed, Integer> colNoOfDays;
        @FXML private TableColumn<AllotBed, Integer> colBedNo;
        @FXML private TableColumn<AllotBed, Integer> colRoomNo;
        @FXML private TableColumn<AllotBed, Integer> colFloorNo;
        @FXML private TableColumn<AllotBed, Double> colFee;

        // ======================================================
        // BACKEND & UTILITY OBJECTS
        // ======================================================
        private final ObjectMapper mapper = new ObjectMapper();
        private final ObservableList<AllotBed> observablePatients = FXCollections.observableArrayList();

        // ======================================================
        // INITIALIZE METHOD
        // ======================================================
        @FXML
        public void initialize() {
                // Connect table columns with getter methods in AllotBed
                colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
                colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
                colNoOfDays.setCellValueFactory(new PropertyValueFactory<>("noOfDays"));
                colBedNo.setCellValueFactory(new PropertyValueFactory<>("bedNo"));
                colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
                colFloorNo.setCellValueFactory(new PropertyValueFactory<>("floorNo"));
                colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

                // Set data source for TableView
                tablePatients.setItems(observablePatients);

                // Fetch initial data automatically from Render / Localhost API
                loadPatientsFromDatabase();
        }

        // ======================================================
        // METHOD: GET /patients
        // ======================================================
        private void loadPatientsFromDatabase() {
                try {
                        String json = APIClient.get("/patients");

                        if (json != null && !json.isEmpty()) {
                                List<AllotBed> patients = mapper.readValue(json, new TypeReference<List<AllotBed>>() {});
                                observablePatients.setAll(patients);
                                tablePatients.refresh();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "API Error", "Could not load data: " + e.getMessage());
                }
        }

        // ======================================================
        // METHOD: POST /patients (ADD BUTTON)
        // ======================================================
        @FXML
        private void addPatient() {
                try {
                        // Input Parsing
                        int patientId = Integer.parseInt(txtPatientId.getText());
                        String patientName = txtPatientName.getText();
                        
                        if (patientName.trim().isEmpty()) {
                                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Patient name cannot be empty.");
                                return;
                        }

                        int noOfDays = Integer.parseInt(txtNoOfDays.getText());
                        int bedNo = Integer.parseInt(txtBedNo.getText());
                        int roomNo = Integer.parseInt(txtRoomNo.getText());
                        int floorNo = Integer.parseInt(txtFloorNo.getText());

                        if (patientId <= 0 || noOfDays <= 0 || bedNo < 0 || roomNo < 0 || floorNo < 0) {
                                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Values cannot be negative.");
                                return;
                        }

                        // Create model payload
                        AllotBed patient = new AllotBed(patientId, patientName, noOfDays, bedNo, roomNo, floorNo);

                        // Convert object to a JSON payload string
                        String jsonRequestBody = mapper.writeValueAsString(patient);

                        // Send HTTP POST to the backend API
                        String response = APIClient.post("/patients", jsonRequestBody);

                        // If backend throws an exception/error, our GlobalExceptionHandler catches it, 
                        // so we check if the response indicates failure or handle it gracefully.
                        if (response != null && !response.contains("error")) {
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient added successfully.");
                                loadPatientsFromDatabase(); // Refresh UI list straight from DB state
                                clearFields();
                        } else {
                                showAlert(Alert.AlertType.ERROR, "API Validation Failure", "Backend rejected request:\n" + response);
                        }

                } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numeric values.");
                } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Network Error", e.getMessage());
                }
        }

        // ======================================================
        // METHOD: PUT /patients/{id} (UPDATE BUTTON)
        // ======================================================
        @FXML
        private void updatePatient() {
                try {
                        int patientId = Integer.parseInt(txtPatientId.getText());
                        String patientName = txtPatientName.getText();

                        if (patientName.trim().isEmpty()) {
                                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Patient name cannot be empty.");
                                return;
                        }

                        int noOfDays = Integer.parseInt(txtNoOfDays.getText());
                        int bedNo = Integer.parseInt(txtBedNo.getText());
                        int roomNo = Integer.parseInt(txtRoomNo.getText());
                        int floorNo = Integer.parseInt(txtFloorNo.getText());

                        AllotBed updatedPatient = new AllotBed(patientId, patientName, noOfDays, bedNo, roomNo, floorNo);
                        String jsonRequestBody = mapper.writeValueAsString(updatedPatient);

                        // Send HTTP PUT request to modify the resource
                        String response = APIClient.put("/patients/" + patientId, jsonRequestBody);

                        if (response != null && !response.contains("error")) {
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient updated successfully.");
                                loadPatientsFromDatabase();
                                clearFields();
                        } else {
                                showAlert(Alert.AlertType.ERROR, "Update Error", response);
                        }

                } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numeric values.");
                } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Network Error", e.getMessage());
                }
        }

        // ======================================================
        // METHOD: DELETE /patients/{id} (REMOVE PATIENT BUTTON)
        // ======================================================
        @FXML
        private void removePatient() {
                try {
                        int patientId = Integer.parseInt(txtSearchId.getText());

                        // Send HTTP DELETE command to the specific resource URL
                        String response = APIClient.delete("/patients/" + patientId);

                        if (response != null && !response.contains("error")) {
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient deleted successfully.");
                                loadPatientsFromDatabase();
                                txtSearchId.clear();
                        } else {
                                showAlert(Alert.AlertType.ERROR, "Deletion Error", "Target patient not found or active.");
                        }

                } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Enter a valid Patient ID.");
                } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Network Error", e.getMessage());
                }
        }

        // ======================================================
        // METHOD: POST /patients/{id}/vacate (VACATE BED BUTTON)
        // ======================================================
        @FXML
        private void vacateBed() {
                try {
                        int patientId = Integer.parseInt(txtSearchId.getText());

                        // Send action request to vacate endpoint
                        String response = APIClient.post("/patients/" + patientId + "/vacate", "");

                        if (response != null && !response.contains("error")) {
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Bed vacated successfully.");
                                loadPatientsFromDatabase();
                                txtSearchId.clear();
                        } else {
                                showAlert(Alert.AlertType.ERROR, "Error", "Could not vacate target bed.");
                        }

                } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Enter a valid Patient ID.");
                } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Network Error", e.getMessage());
                }
        }

        // ======================================================
        // SEARCH PATIENT LOCAL UI HOVER
        // ======================================================
        @FXML
        private void searchPatient() {
                try {
                        int patientId = Integer.parseInt(txtSearchId.getText());

                        for (AllotBed patient : observablePatients) {
                                if (patient.getPatientId() == patientId) {
                                        tablePatients.getSelectionModel().select(patient);
                                        tablePatients.scrollTo(patient);
                                        showAlert(Alert.AlertType.INFORMATION, "Patient Found", "Patient: " + patient.getPatientName());
                                        return;
                                }
                        }
                        showAlert(Alert.AlertType.WARNING, "Not Found", "Patient not found in the active table view.");
                } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Enter a valid Patient ID.");
                }
        }

        @FXML
        private void displayAllPatients() {
                loadPatientsFromDatabase();
        }

        private void clearFields() {
                txtPatientId.clear();
                txtPatientName.clear();
                txtNoOfDays.clear();
                txtBedNo.clear();
                txtRoomNo.clear();
                txtFloorNo.clear();
        }

        private void showAlert(Alert.AlertType type, String title, String message) {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }
}