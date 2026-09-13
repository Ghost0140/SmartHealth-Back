package com.historial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.historial.feign.ClienteDoctorFeign;
import com.historial.feign.ClientePacienteFeign;
import com.historial.feign.DoctorFeign;
import com.historial.feign.PacienteFeign;
import com.historial.model.HistorialClinico;
import com.historial.repository.IHistorialRepository;

@Service
public class HistorialMedicoService {

    @Autowired
    private IHistorialRepository repository;

    @Autowired
    private ClientePacienteFeign pacienteFeign;

    @Autowired
    private ClienteDoctorFeign doctorFeign;

    public HistorialClinico registrarHistorial(HistorialClinico h) {

    	/*
        PacienteFeign paciente =
                pacienteFeign.obtenerPacientePorId(h.getIdPaciente());

        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }

        DoctorFeign doctor =
                doctorFeign.obtenerDoctorPorId(h.getIdDoctor());

        if (doctor == null) {
            throw new RuntimeException("Doctor no encontrado");
        } */

        return repository.save(h);
    }

    public HistorialClinico obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<HistorialClinico> listarHistorial() {
        return repository.findAll();
    }

    public List<HistorialClinico> listarPorPaciente(Integer pacienteId) {
        return repository.findByIdPaciente(pacienteId);
    }
}