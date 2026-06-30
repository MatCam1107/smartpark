package com.smartpark.ms_pago.service;

import com.smartpark.ms_pago.dto.TipoPagoRequestDTO;
import com.smartpark.ms_pago.dto.TipoPagoResponseDTO;
import com.smartpark.ms_pago.model.TipoPago;
import com.smartpark.ms_pago.repository.TipoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoPagoService {

    private final TipoPagoRepository tipoPagoRepository;

    private TipoPagoResponseDTO convertirADTO(TipoPago tipoPago) {
        return new TipoPagoResponseDTO(
                tipoPago.getIdTipoPago(),
                tipoPago.getNombreTipoPago(),
                tipoPago.getDescripcion()
        );
    }

    private TipoPago convertirAEntidad(TipoPagoRequestDTO dto) {
        TipoPago tipoPago = new TipoPago();

        tipoPago.setNombreTipoPago(dto.getNombreTipoPago());
        tipoPago.setDescripcion(dto.getDescripcion());

        return tipoPago;
    }

    public List<TipoPagoResponseDTO> obtenerTodos() {
        return tipoPagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<TipoPagoResponseDTO> obtenerPorId(Long id) {
        return tipoPagoRepository.findById(id)
                .map(this::convertirADTO);
    }

    public TipoPagoResponseDTO guardar(TipoPagoRequestDTO dto) {
        TipoPago tipoPago = convertirAEntidad(dto);
        TipoPago guardado = tipoPagoRepository.save(tipoPago);
        return convertirADTO(guardado);
    }

    public Optional<TipoPagoResponseDTO> actualizar(Long id, TipoPagoRequestDTO dto) {
        return tipoPagoRepository.findById(id).map(tp -> {

            tp.setNombreTipoPago(dto.getNombreTipoPago());
            tp.setDescripcion(dto.getDescripcion());

            TipoPago actualizado = tipoPagoRepository.save(tp);

            return convertirADTO(actualizado);
        });
    }

    public void eliminar(Long id) {
        tipoPagoRepository.deleteById(id);
    }
}