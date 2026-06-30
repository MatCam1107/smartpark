package com.smartpark.ms_estacionamiento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estacionamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estacionamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estacionamiento")
    private Long idEstacionamiento;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(name = "tarifa_hora", nullable = false)
    private Double tarifaHora;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "ubicacion_id", nullable = false)
    private Long ubicacionId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
}