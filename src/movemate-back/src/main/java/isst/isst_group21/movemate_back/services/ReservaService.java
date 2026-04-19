package isst.isst_group21.movemate_back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isst.isst_group21.movemate_back.models.Reserva;
import isst.isst_group21.movemate_back.repositories.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva confirmarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setConfirmada(true);
        reserva.setEnProceso(false);
        reserva.setCancelada(false);
        return reservaRepository.save(reserva);
    }

    public Reserva cancelarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setCancelada(true);
        reserva.setEnProceso(false);
        reserva.setConfirmada(false);
        return reservaRepository.save(reserva);
    }
    
}
