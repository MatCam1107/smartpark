package com.smartpark.msubicacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ubicacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long idUbicacion;

    @Column(nullable = false, length = 150)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String comuna;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;
}