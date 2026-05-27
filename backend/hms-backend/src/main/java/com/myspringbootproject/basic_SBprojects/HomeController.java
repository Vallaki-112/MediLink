package com.myspringbootproject.basic_SBprojects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Hello Spring Boot!";
    }

    @GetMapping("/hospital")
    public String hospital() {
        return "Hospital Manage System";

    }

    @GetMapping("/student")
    public Student stud() {

        return new Student(
                101,
                "Rahul");
    }
}