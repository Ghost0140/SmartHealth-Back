package com.smarthealth.pharmacy.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.pharmacy.dto.MedicamentoCreateDto;
import com.smarthealth.pharmacy.dto.MedicamentoResponseDto;
import com.smarthealth.pharmacy.dto.MedicamentoUpdateDto;
import com.smarthealth.pharmacy.entity.MedicamentoEntity;

@Component
public class MedicamentoMapper {
	
	public MedicamentoEntity toEntity(MedicamentoCreateDto dto) {
		MedicamentoEntity medicamento = new MedicamentoEntity();

		medicamento.setNombre(dto.nombre());
		medicamento.setStock(dto.stock());
		medicamento.setPrecio(dto.precio());
		medicamento.setActivo(true);

		return medicamento;
	}
	
	public MedicamentoResponseDto toResponseDto(MedicamentoEntity medicamento) {
        return new MedicamentoResponseDto(
                medicamento.getIdMedicamento(),
                medicamento.getNombre(),
                medicamento.getStock(),
                medicamento.getPrecio(),
                medicamento.getActivo()
        );
    }
	
	public void updateEntity(MedicamentoUpdateDto dto, MedicamentoEntity medicamento) {
		medicamento.setNombre(dto.nombre());
		medicamento.setStock(dto.stock());
		medicamento.setPrecio(dto.precio());
	}

}
