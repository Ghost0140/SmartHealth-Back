package com.SmartHealth.pacientes_service.service;

import com.SmartHealth.pacientes_service.model.Paciente;
import com.SmartHealth.pacientes_service.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repo;

    public List<Paciente> listarPacientes() {
        return repo.findAll();
    }

    public Paciente pacientePorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Paciente registrarPaciente(Paciente paciente) {
        return repo.save(paciente);
    }

    public Paciente editarPaciente(Integer id, Paciente pacienteActualizado) {
        Paciente pacienteExistente = repo.findById(id).orElse(null);

        if (pacienteExistente != null) {
            pacienteExistente.setTelefono(pacienteActualizado.getTelefono());
            pacienteExistente.setEmail(pacienteActualizado.getEmail());


            return repo.save(pacienteExistente);
        }
        return null;
    }

    public Paciente cambiarEstadoPaciente(Integer id) {
        Paciente pacienteExistente = repo.findById(id).orElse(null);

        if (pacienteExistente != null) {

            Boolean estadoActual = pacienteExistente.getEstado();

            if (estadoActual == null) {
                pacienteExistente.setEstado(false);
            } else {
                pacienteExistente.setEstado(!estadoActual);
            }

            return repo.save(pacienteExistente);
        }

        return null;
    }




}
