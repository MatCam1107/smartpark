package com.smartpark.ms_pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartpark.ms_pago.model.TipoPago;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {

}