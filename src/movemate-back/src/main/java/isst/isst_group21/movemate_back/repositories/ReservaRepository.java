package isst.isst_group21.movemate_back.repositories;

import isst.isst_group21.movemate_back.models.Reserva;
import isst.isst_group21.movemate_back.models.Usuario;
import isst.isst_group21.movemate_back.models.Actividad;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByConfirmada(boolean confirmada);
    List<Reserva> findByCancelada(boolean cancelada);
    List<Reserva> findByEnProceso(boolean enProceso);
    List<Reserva> findByUsuario_IdUsuario(String idUsuario); 

    //Reserva de una actividad por un usuario
    Optional<Reserva> findByIdReservaAndUsuario_IdUsuario(Long idReserva, String idUsuario);

    //Reserva de una actividad
    List<Reserva> findByActividad(Actividad actividad);

    //Reserva de un usuario
    List<Reserva> findByUsuario(Usuario usuario);

    // Reservas que no han sido canceladas por un usuario
    int countByActividadAndCanceladaFalse(Actividad actividad);

    //Reservas confirmadas de un usuario (con @Query)
    @Query("SELECT r FROM Reserva r WHERE r.usuario.idUsuario = :idUsuario AND r.confirmada = true")
    List<Reserva> findReservasConfirmadasByUsuario(String idUsuario);

    //Reservas de un usuario (con @Query)
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.actividad.idActividad = :idActividad")
    int countReservasByActividadId(@Param("idActividad") String idActividad);
}