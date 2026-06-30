package com.smartpark.ms_pago.service;

import com.smartpark.ms_pago.dto.PagoRequestDTO;
import com.smartpark.ms_pago.dto.PagoResponseDTO;
import com.smartpark.ms_pago.model.Pago;
import com.smartpark.ms_pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    private PagoResponseDTO convertirADTO(Pago pago) {
        return new PagoResponseDTO(
                pago.getIdPago(),
                pago.getMonto(),
                pago.getFechaPago(),
                pago.getEstado(),
                pago.getTipoPagoId(),
                pago.getReservaId()
        );
    }

    private Pago convertirAEntidad(PagoRequestDTO dto) {
        Pago pago = new Pago();

        pago.setMonto(dto.getMonto());
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstado(dto.getEstado());
        pago.setTipoPagoId(dto.getTipoPagoId());
        pago.setReservaId(dto.getReservaId());

        return pago;
    }

    public List<PagoResponseDTO> obtenerTodos() {
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<PagoResponseDTO> obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .map(this::convertirADTO);
    }

    public PagoResponseDTO guardar(PagoRequestDTO dto) {
        Pago pago = convertirAEntidad(dto);
        Pago guardado = pagoRepository.save(pago);
        return convertirADTO(guardado);
    }

    public Optional<PagoResponseDTO> actualizar(Long id, PagoRequestDTO dto) {
        return pagoRepository.findById(id).map(p -> {

            p.setMonto(dto.getMonto());
            p.setFechaPago(dto.getFechaPago());
            p.setEstado(dto.getEstado());
            p.setTipoPagoId(dto.getTipoPagoId());
            p.setReservaId(dto.getReservaId());

            Pago actualizado = pagoRepository.save(p);

            return convertirADTO(actualizado);
        });
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}