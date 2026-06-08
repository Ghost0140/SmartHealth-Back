package com.farmacia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.farmacia.model.Medicamento;
import com.farmacia.service.MedicamentoService;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService service;

    @PostMapping
    public ResponseEntity<Medicamento> registrar(@RequestBody Medicamento medicamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(medicamento));
    }

    @GetMapping
    public List<Medicamento> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscar(@PathVariable Integer id) {
        Medicamento med = service.buscarPorId(id);

        if (med == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(med);
    }

    @PutMapping("/{id}")
    public Medicamento actualizar(@PathVariable Integer id, @RequestBody Medicamento medicamento) {
        return service.actualizar(id, medicamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}