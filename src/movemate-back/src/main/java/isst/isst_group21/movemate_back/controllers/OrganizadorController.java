package isst.isst_group21.movemate_back.controllers;

import isst.isst_group21.movemate_back.DTO.ActividadDTO;
import isst.isst_group21.movemate_back.DTO.RegistroDTO;
import isst.isst_group21.movemate_back.models.Actividad;
import isst.isst_group21.movemate_back.models.Organizador;
import isst.isst_group21.movemate_back.models.Reserva;
import isst.isst_group21.movemate_back.services.OrganizadorService;
import isst.isst_group21.movemate_back.services.ReservaService;
import isst.isst_group21.movemate_back.services.ActividadService;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController  
@RequestMapping("/organizadores")  
@CrossOrigin(origins = "http://localhost:8080")  

public class OrganizadorController {
    
    @Autowired
    private OrganizadorService organizadorService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ActividadService actividadService;

    //Crear actividad
    @PostMapping(value = "/{idOrg}/misActividades/crearActividad", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Actividad> crearActividad(@ModelAttribute ActividadDTO dto, @PathVariable String idOrg) {
        Actividad actividad = organizadorService.crearActividad(dto, idOrg);
        return ResponseEntity.ok(actividad);
    }

    //Actualizar actividad
    @PutMapping("/{idOrg}/{idActividad}/modificarActividad")
    public ResponseEntity<Actividad> actualizarActividad(@RequestBody ActividadDTO dto, @PathVariable String idOrg, @PathVariable String idActividad){
        Actividad actividad = organizadorService.actualizarActividad(idActividad, dto, idOrg);
        return ResponseEntity.ok(actividad);
    }

    //Eliminar actividad
    @DeleteMapping("/{idOrg}/{idActividad}/eliminarActividad")
    public ResponseEntity<String> eliminarActividadOrganizador(@PathVariable String idOrg, @PathVariable String idActividad) {
        actividadService.eliminarActividadComoOrganizador(idOrg, idActividad);
        return ResponseEntity.ok("Actividad eliminada correctamente");
    }

    //Obtener actividades de un organizador
    @GetMapping("/{idOrg}/actividades")
    public ResponseEntity<List<Actividad>> obtenerActividades(@PathVariable String idOrg) {
        List<Actividad> actividades = organizadorService.obtenerActividadesPorOrganizador(idOrg);
        return ResponseEntity.ok(actividades);
    }

    //Confirmar reserva
    @PutMapping("/{idOrg}/{idReserva}/confirmarReserva")
    public ResponseEntity<Reserva> confirmarReserva(@PathVariable Long idReserva) {
        Reserva reserva = reservaService.confirmarReserva(idReserva);
        return ResponseEntity.ok(reserva);
    }

    //Cancelar reserva
    @PutMapping("/{idOrg}/{idReserva}/cancelarReserva")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long idReserva) {
        Reserva reserva = reservaService.cancelarReserva(idReserva);
        return ResponseEntity.ok(reserva);
    }

    //Ver perfil
    @GetMapping("/profile/{idOrg}")
    public ResponseEntity<Organizador> verPerfil(@PathVariable String idOrg) {
        return ResponseEntity.ok(organizadorService.verPerfil(idOrg));
    }

    //Actualizar perfil
    @PutMapping("/profile/{idOrg}")
    public ResponseEntity<Organizador> actualizarPerfil(@PathVariable String idOrg, @RequestBody RegistroDTO dto) {
        return ResponseEntity.ok(organizadorService.actualizarPerfil(idOrg, dto));
    }
    
    // Borrar perfil de usuario
    @DeleteMapping("/profile/{idOrg}")
    public ResponseEntity<String> borrarOrganizador(@PathVariable String idOrg) {
        organizadorService.borrarOrganizador(idOrg);
        return ResponseEntity.ok("Organizador eliminado correctamente");
    }

}
