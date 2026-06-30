package com.smartpark.ms_estacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartpark.ms_estacionamiento.model.Estacionamiento;

@Repository
public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {

}