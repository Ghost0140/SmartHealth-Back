package com.farmacia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmacia.dto.RegistroRecetaDTO;
import com.farmacia.feign.CitaFeign;
import com.farmacia.feign.CitaFeignCliente;
import com.farmacia.model.Medicamento;
import com.farmacia.model.Receta;
import com.farmacia.rabbit.MensajeProductor;
import com.farmacia.rabbit.RecetaEvent;
import com.farmacia.repository.IMedicamentoRepository;
import com.farmacia.repository.IRecetaRepository;

import feign.FeignException;

@Service
public class RecetaService {

    @Autowired
    private IRecetaRepository recetaRepo;

    @Autowired
    private IMedicamentoRepository medicamentoRepo;

    @Autowired
    private CitaFeignCliente citaFeign;

    @Autowired
    private MensajeProductor productor;

    public List<Receta> listar() {
        return recetaRepo.findAll();
    }

    public Receta registrarReceta(RegistroRecetaDTO dto) {

        CitaFeign cita;

        try {
            cita = citaFeign.obtenerCitaPorId(dto.getIdCita());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Cita no encontrada con id: " + dto.getIdCita());
        }

        if (cita == null) {
            throw new RuntimeException("Cita no encontrada con id: " + dto.getIdCita());
        }

        Medicamento medicamento = medicamentoRepo.findById(dto.getIdMedicamento())
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con id: " + dto.getIdMedicamento()));

        if (!Boolean.TRUE.equals(medicamento.getActivo())) {
            throw new RuntimeException("El medicamento está inactivo");
        }

        if (medicamento.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el medicamento: " + medicamento.getNombre());
        }

        medicamento.setStock(medicamento.getStock() - dto.getCantidad());
        medicamentoRepo.save(medicamento);

        Receta receta = new Receta();
        receta.setIdCita(dto.getIdCita());
        receta.setIdMedicamento(medicamento.getIdMedicamento());
        receta.setNombreMedicamento(medicamento.getNombre());
        receta.setCantidad(dto.getCantidad());
        receta.setFechaReceta(LocalDate.now());
        receta.setEstado("GENERADA");

        Receta recetaGuardada = recetaRepo.save(receta);

        RecetaEvent event = new RecetaEvent(
                recetaGuardada.getIdReceta(),
                recetaGuardada.getIdCita(),
                recetaGuardada.getIdMedicamento(),
                recetaGuardada.getNombreMedicamento(),
                recetaGuardada.getCantidad(),
                recetaGuardada.getFechaReceta(),
                "Receta generada correctamente"
        );

        productor.enviarRecetaGenerada(event);

        return recetaGuardada;
    }
}