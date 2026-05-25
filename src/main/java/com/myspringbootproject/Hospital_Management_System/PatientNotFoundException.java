package com.myspringbootproject.Hospital_Management_System;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(
            String message) {

        super(message);
    }
}

