package com.SmartHealth.doctores_service.service;

import com.SmartHealth.doctores_service.model.Doctor;
import com.SmartHealth.doctores_service.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repo;

    public List<Doctor> listarDoctores() {
        return repo.findAll();
    }

    public Doctor doctorPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Doctor registrarDoctor(Doctor doctor) {
        return repo.save(doctor);
    }

    public Doctor editarDoctor(Integer id, Doctor doctorActualizado) {
        Doctor DoctorExistente = repo.findById(id).orElse(null);

        if (DoctorExistente != null) {
            DoctorExistente.setEspecialidad(doctorActualizado.getEspecialidad());
            DoctorExistente.setDisponibilidad(doctorActualizado.getDisponibilidad());


            return repo.save(DoctorExistente);
        }
        return null;
    }

    public Doctor cambiarEstadoDoctor(Integer id) {
        Doctor doctorExistente = repo.findById(id).orElse(null);

        if (doctorExistente != null) {

            Boolean estadoActual = doctorExistente.getEstado();

            if (estadoActual == null) {
                doctorExistente.setEstado(false);
            } else {
                doctorExistente.setEstado(!estadoActual);
            }

            return repo.save(doctorExistente);
        }

        return null;


    }
}

