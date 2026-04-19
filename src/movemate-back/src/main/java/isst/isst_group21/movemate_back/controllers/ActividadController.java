package isst.isst_group21.movemate_back.controllers;
import org.springframework.web.bind.annotation.RestController;

import isst.isst_group21.movemate_back.DTO.ActividadDTO;
import isst.isst_group21.movemate_back.enums.Categorias;
import isst.isst_group21.movemate_back.models.Actividad;
import isst.isst_group21.movemate_back.services.ActividadService;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController  
@RequestMapping("/actividad")  
@CrossOrigin(origins = "http://localhost:8080")
public class ActividadController {
    
    @Autowired
    private ActividadService actividadService;

    //Obtener una actividad
    @GetMapping("{idActividad}/")
    public ResponseEntity<ActividadDTO> verDetalle(@PathVariable String idActividad) {
        Actividad actividad = actividadService.obtenerPorId(idActividad);
        if (actividad == null) {
            return ResponseEntity.notFound().build();
        }
        
        ActividadDTO dto = new ActividadDTO(actividad);
        return ResponseEntity.ok(dto);
    }

    //Obtener numero de reservas de una actividad
    @GetMapping("/{id}/reservas")
    public ResponseEntity<Integer> contarReservas(@PathVariable("id") String id) {
    int numReservas = actividadService.contarReservas(id);
    return ResponseEntity.ok(numReservas);
}

    //Buscar actividad por nombre
    @GetMapping("/buscar")
    public List<Actividad> buscarActividades(@RequestParam String nombre) {
        return actividadService.buscarActividadesPorPalabraClave(nombre);
    }

    //Buscar actividad con todos los parametros
    @GetMapping("/buscar-completo")
    public ResponseEntity<List<Actividad>> buscarActividadesCompleto(@RequestParam String nombre, @RequestParam String categoria, @RequestParam String fecha) {
        Categorias categoriaEnum;
        try {
            categoriaEnum = Categorias.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Categoría no válida
        }
        List<Actividad> actividades = actividadService.filtrarPorCategoria(categoriaEnum);
        return ResponseEntity.ok(actividades);
    }

    //Obtener todas las categorias (desplegable)
    @GetMapping("/categorias")
    public ResponseEntity<Categorias[]> obtenerCategorias() {
        return ResponseEntity.ok(Categorias.values());
    }

    //Obtener imagen de la actividad
    @GetMapping("/{idActividad}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable String idActividad) {
        byte[] imagen = actividadService.obtenerPorId(idActividad).getFoto();
        if (imagen != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    //Filtrar por categoria
    @GetMapping("/filtrar-categoria")
    public ResponseEntity<List<ActividadDTO>> filtrarPorCategoria(@RequestParam Categorias categoria) {
        List<Actividad> actividades = actividadService.filtrarPorCategoria(categoria);
        List<ActividadDTO> actividadDTOs = actividades.stream()
            .map(ActividadDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(actividadDTOs);
    }
    

    // Filtrar actividades a 15km de una ubicación
    @GetMapping("/cercanas")
    public ResponseEntity<List<ActividadDTO>> getNearbyActivities(@RequestParam double lat, @RequestParam double lon, @RequestParam(required = false) Categorias categoria) {
        List<Actividad> actividades = actividadService.getActivitiesNearby(lat, lon, 15);
        if (categoria != null) {
            actividades = actividades.stream().filter(a -> a.getCategoria().equals(categoria)).collect(Collectors.toList());
        }
        List<ActividadDTO> actividadDTOs = actividades.stream().map(a -> new ActividadDTO(a)).collect(Collectors.toList());
        return ResponseEntity.ok(actividadDTOs);
    }

}

