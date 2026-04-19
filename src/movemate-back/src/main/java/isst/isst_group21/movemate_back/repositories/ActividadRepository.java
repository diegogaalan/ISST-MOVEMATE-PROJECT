package isst.isst_group21.movemate_back.repositories;

import java.util.List;
import isst.isst_group21.movemate_back.models.Actividad;
import isst.isst_group21.movemate_back.models.Organizador;
import isst.isst_group21.movemate_back.enums.Categorias;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, String> {

    List<Actividad> findByNombre(String nombre);
    List<Actividad> findByFechaHora(LocalDateTime fechaHora);
    List<Actividad> findByPrecio(double precio);
    List<Actividad> findByUbicacion(String ubicacion);
    List<Actividad> findByCategoria(Categorias categoria);
    
    List<Actividad> findByConfirmada(boolean confirmada);

    //Busqueda por organizador
    List<Actividad> findByOrganizador(Organizador organizador);
    List<Actividad> findByOrganizador_IdOrg(String idOrg);

    //Busqueda por palabra clave en el nombre de la actividad. Para la barra de busqueda
    List<Actividad> findByNombreContainingIgnoreCase(String palabraClave);
    
    // Busqueda por nombre categoria y fechaHora
    List<Actividad> findByNombreAndCategoriaAndFechaHora(String nombre, Categorias categoria, LocalDateTime fechaHora);

    // Busqueda por nombre y categoria
    List<Actividad> findByNombreAndCategoria(String nombre, Categorias categoria);

    // Busqueda por nombre y fechaHora
    List<Actividad> findByNombreAndFechaHora(String nombre, LocalDateTime fechaHora);

    // Busqueda por categoria y fechaHora
    List<Actividad> findByCategoriaAndFechaHora(Categorias categoria, LocalDateTime fechaHora);

    //Actividades futuras confirmadas ordenadas por fecha
    @Query("SELECT a FROM Actividad a WHERE a.confirmada = true ORDER BY a.fechaHora ASC")
    List<Actividad> findConfirmadasOrdenadas();

    // Actividades cercanas a una ubicación dada
    @Query("SELECT a FROM Actividad a WHERE (6371 * acos(cos(radians(:userLat)) * cos(radians(a.lat)) * cos(radians(a.lon) - radians(:userLon)) + sin(radians(:userLat)) * sin(radians(a.lat)))) <= :maxDistance")
    List<Actividad> findActividadesCercanas(@Param("userLat") double userLat,
                                            @Param("userLon") double userLon,
                                            @Param("maxDistance") double maxDistance);
    
}