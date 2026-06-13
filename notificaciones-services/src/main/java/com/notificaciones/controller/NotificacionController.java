package com.notificaciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificaciones.model.Notificacion;
import com.notificaciones.service.NotificacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @GetMapping
    public List<Notificacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscar(
            @PathVariable Integer id) {

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> marcarLeida(
            @PathVariable Integer id) {

        return service.marcarLeida(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        return service.eliminar(id);
    }
}