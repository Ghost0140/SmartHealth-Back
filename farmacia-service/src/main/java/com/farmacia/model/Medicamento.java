package com.farmacia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedicamento;

    private String nombre;
    private String descripcion;
    private Integer stock;
    private Double precio;
    private Boolean activo = true;
}