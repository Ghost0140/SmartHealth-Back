package com.SmartHealth.pacientes_service.controller;

import com.SmartHealth.pacientes_service.model.Paciente;
import com.SmartHealth.pacientes_service.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(service.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable Integer id) {
        Paciente paciente = service.pacientePorId(id);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@Valid @RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarPaciente(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> editarPaciente(@PathVariable Integer id, @Valid @RequestBody Paciente paciente) {
        Paciente pacienteActualizado = service.editarPaciente(id, paciente);
        if (pacienteActualizado != null) {
            return ResponseEntity.ok(pacienteActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Paciente> eliminarPaciente(@PathVariable Integer id) {
        Paciente pacienteDesactivado = service.cambiarEstadoPaciente(id);
        if (pacienteDesactivado != null) {
            return ResponseEntity.ok(pacienteDesactivado);
        }
        return ResponseEntity.notFound().build();
    }
}