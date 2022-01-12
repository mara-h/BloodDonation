package com.blooddonation.controller;

import com.blooddonation.model.Appointment;
import com.blooddonation.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable UUID id) {
        return appointmentService.getAppointmentById(id);
    }
    @PostMapping("/appointments")
    public ResponseEntity<String> addAppointment(@RequestBody Appointment appointment) {
        return appointmentService.addAppointment(appointment);
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable UUID id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

    @DeleteMapping("/appointments")
    public ResponseEntity<String> deleteAllAppointments() {
        return appointmentService.deleteAllAppointments();
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable UUID id) {
        return appointmentService.deleteAppointment(id);
    }
}
