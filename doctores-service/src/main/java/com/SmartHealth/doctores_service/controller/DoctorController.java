package com.SmartHealth.doctores_service.controller;

import com.SmartHealth.doctores_service.model.Doctor;
import com.SmartHealth.doctores_service.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @GetMapping
    public ResponseEntity<List<Doctor>> listarDoctores() {
        return ResponseEntity.ok(service.listarDoctores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> obtenerDoctorPorId(@PathVariable Integer id) {
        Doctor doctor = service.doctorPorId(id);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Doctor> registrarDoctor(@Valid @RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarDoctor(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> editarDoctor(@PathVariable Integer id, @Valid @RequestBody Doctor doctor) {
        Doctor doctorActualizado = service.editarDoctor(id, doctor);
        if (doctorActualizado != null) {
            return ResponseEntity.ok(doctorActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Doctor> eliminarDoctor(@PathVariable Integer id) {
        Doctor doctorDesactivado = service.cambiarEstadoDoctor(id);
        if (doctorDesactivado != null) {
            return ResponseEntity.ok(doctorDesactivado);
        }
        return ResponseEntity.notFound().build();
    }
}