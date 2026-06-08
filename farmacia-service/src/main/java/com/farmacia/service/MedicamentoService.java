package com.farmacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmacia.model.Medicamento;
import com.farmacia.repository.IMedicamentoRepository;

@Service
public class MedicamentoService {

    @Autowired
    private IMedicamentoRepository repo;

    public Medicamento registrar(Medicamento medicamento) {
        medicamento.setActivo(true);
        return repo.save(medicamento);
    }

    public List<Medicamento> listar() {
        return repo.findAll();
    }

    public Medicamento buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Medicamento actualizar(Integer id, Medicamento datos) {
        Medicamento med = buscarPorId(id);

        if (med == null) {
            throw new RuntimeException("Medicamento no encontrado con id: " + id);
        }

        med.setNombre(datos.getNombre());
        med.setDescripcion(datos.getDescripcion());
        med.setStock(datos.getStock());
        med.setPrecio(datos.getPrecio());
        med.setActivo(datos.getActivo());

        return repo.save(med);
    }

    public void eliminar(Integer id) {
        Medicamento med = buscarPorId(id);

        if (med == null) {
            throw new RuntimeException("Medicamento no encontrado con id: " + id);
        }

        med.setActivo(false);
        repo.save(med);
    }
}