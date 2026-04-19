package isst.isst_group21.movemate_back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import isst.isst_group21.movemate_back.enums.Categorias;
import isst.isst_group21.movemate_back.models.Actividad;
import isst.isst_group21.movemate_back.repositories.ActividadRepository;
import isst.isst_group21.movemate_back.repositories.OrganizadorRepository;
import isst.isst_group21.movemate_back.repositories.UsuarioRepository;
import isst.isst_group21.movemate_back.repositories.ReservaRepository;

@Service
public class ActividadService {


    @Autowired 
    ActividadRepository actividadRepository;

    @Autowired
    OrganizadorRepository organizadorRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReservaRepository reservaRepository;

    public ActividadService(UsuarioRepository usuarioRepository, ActividadRepository actividadRepository, OrganizadorRepository organizadorRepository) {
        this.actividadRepository = actividadRepository;
    }

    //Obtener una actividad
    public Actividad obtenerPorId(String id) {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

                
    }

    //Busqueda de actividades completas
    public List<Actividad> buscarActividadesCompleto(String nombre, Categorias categoria, String fechaHora) {
        if (nombre != null && categoria != null && fechaHora != null) {
            return actividadRepository.findByNombreAndCategoriaAndFechaHora(nombre, categoria, LocalDateTime.parse(fechaHora));
        } else if (nombre != null && categoria != null) {
            return actividadRepository.findByNombreAndCategoria(nombre, categoria);
        } else if (nombre != null && fechaHora != null) {
            return actividadRepository.findByNombreAndFechaHora(nombre, LocalDateTime.parse(fechaHora));
        } else if (categoria != null && fechaHora != null) {
            return actividadRepository.findByCategoriaAndFechaHora(categoria, LocalDateTime.parse(fechaHora));
        } else if (nombre != null) {
            return actividadRepository.findByNombre(nombre);
        } else if (categoria != null) {
            return actividadRepository.findByCategoria(categoria);
        } else if (fechaHora != null) {
            return actividadRepository.findByFechaHora(LocalDateTime.parse(fechaHora));
        } else {
            return actividadRepository.findAll();
        }
    }

    //Busqueda de actividades por palabra clave
    public List<Actividad> buscarActividadesPorPalabraClave(String palabraClave) {
        return actividadRepository.findByNombreContainingIgnoreCase(palabraClave);
    }

    // Filtra actividades por categoria
    public List<Actividad> filtrarPorCategoria(Categorias categoria) {
        return actividadRepository.findByCategoria(categoria);
    }

    //Eliminar actividad como organizador
    public void eliminarActividadComoOrganizador(String idOrg, String idActividad) {
        Actividad actividad = actividadRepository.findById(idActividad)
            .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        if (!actividad.getOrganizador().getIdOrg().equals(idOrg)) {
            throw new RuntimeException("No autorizado para eliminar esta actividad");
        }
        actividadRepository.delete(actividad);
    }

    //Eliminar actividad como admin
    public void eliminarActividadComoAdmin(String idActividad) {
        Actividad actividad = actividadRepository.findById(idActividad)
            .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        actividadRepository.delete(actividad);
    }
    
    //Filtrar actividades a cierta distancia
    public List<Actividad> getActivitiesNearby(double userLat, double userLon, double maxDistance) {
        return actividadRepository.findActividadesCercanas(userLat, userLon, maxDistance);
    }

    public int contarReservas(String idActividad) {
        return reservaRepository.countReservasByActividadId(idActividad);
    }

}





