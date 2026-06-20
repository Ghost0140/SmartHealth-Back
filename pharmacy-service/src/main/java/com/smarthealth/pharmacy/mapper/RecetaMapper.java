package com.smarthealth.pharmacy.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.smarthealth.pharmacy.dto.RecetaCreateDto;
import com.smarthealth.pharmacy.dto.RecetaResponseDto;
import com.smarthealth.pharmacy.entity.MedicamentoEntity;
import com.smarthealth.pharmacy.entity.RecetaEntity;
import com.smarthealth.pharmacy.event.RecetaCreatedEvent;

@Component
public class RecetaMapper {
	
	public RecetaEntity toEntity(RecetaCreateDto dto, MedicamentoEntity medicamento) {
		RecetaEntity receta = new RecetaEntity();

		receta.setIdCita(dto.idCita());
		receta.setMedicamento(medicamento);
		receta.setCantidad(dto.cantidad());
		receta.setFechaRegistro(LocalDateTime.now());

		return receta;
	}
	
	public RecetaResponseDto toResponseDto(RecetaEntity receta) {
        return new RecetaResponseDto(
        		receta.getIdReceta(),
        		receta.getIdCita(),
        		receta.getMedicamento().getIdMedicamento(),
        		receta.getMedicamento().getNombre(),
        		receta.getCantidad(),
        		receta.getFechaRegistro()
        );
    }
	
	public RecetaCreatedEvent toEvent(RecetaEntity entity, Integer idPaciente) {
		return new RecetaCreatedEvent(
				entity.getIdReceta(),
				entity.getIdCita(),
				idPaciente,
				entity.getMedicamento().getIdMedicamento(),
				entity.getMedicamento().getNombre(),
				entity.getCantidad(),
				entity.getFechaRegistro()
		);
	}

}
