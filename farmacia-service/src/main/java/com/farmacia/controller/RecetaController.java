package com.farmacia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.farmacia.dto.RegistroRecetaDTO;
import com.farmacia.model.Receta;
import com.farmacia.service.RecetaService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Receta registrar(@Valid @RequestBody RegistroRecetaDTO dto){
        return service.registrarReceta(dto);
    }

    @GetMapping
    public List<Receta> listar() {
        return service.listar();
    }
}