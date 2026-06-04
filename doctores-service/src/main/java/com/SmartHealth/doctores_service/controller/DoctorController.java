package com.SmartHealth.doctores_service.controller;

import com.SmartHealth.doctores_service.model.Doctor;
import com.SmartHealth.doctores_service.service.DoctorService;
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
        List<Doctor> doctores = service.listarDoctores();
        return new ResponseEntity<>(doctores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> obtenerDoctorPorId(@PathVariable Integer id) {
        Doctor doctor = service.doctorPorId(id);
        if (doctor != null) {
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Doctor> registrarDoctor(@RequestBody Doctor doctor) {
        Doctor nuevoDoctor = service.registrarDoctor(doctor);
        return new ResponseEntity<>(nuevoDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> editarDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        Doctor doctorActualizado = service.editarDoctor(id, doctor);
        if (doctorActualizado != null) {
            return new ResponseEntity<>(doctorActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Doctor> eliminarDoctor(@PathVariable Integer id) {
        Doctor doctorDesactivado = service.cambiarEstadoDoctor(id);
        if (doctorDesactivado != null) {
            return new ResponseEntity<>(doctorDesactivado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}