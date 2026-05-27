package com.hms.allotmentservice.controller;

import com.hms.allotmentservice.model.AllotBed;
import com.hms.allotmentservice.service.AllotmentService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")

public class AllotmentController {

    private final AllotmentService allotmentService;

    public AllotmentController(AllotmentService allotmentService) {
        this.allotmentService = allotmentService;
    }

    @GetMapping("/beds")
    public List<AllotBed> getAllBeds() {
        return allotmentService.getAllAllotments();
    }

    @PostMapping("/allot")
    public String allotBed(@Valid @RequestBody AllotBed allotment) {
        allotmentService.allotBed(allotment);
        return "Bed Allotted Successfully";
    }

    @DeleteMapping("/{id}/vacate")
    public String vacateBed(@PathVariable int id) {
        int rowsAffected = allotmentService.vacateBed(id);
        if (rowsAffected > 0) {
            return "Bed Vacated Successfully";
        }
        return "Allotment Record Not Found";
    }

}
