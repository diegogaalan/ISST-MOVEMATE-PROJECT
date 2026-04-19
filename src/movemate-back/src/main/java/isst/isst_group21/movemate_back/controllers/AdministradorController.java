package isst.isst_group21.movemate_back.controllers;

import org.springframework.web.bind.annotation.RestController;

import isst.isst_group21.movemate_back.services.ActividadService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController  
@RequestMapping("/admin")  
@CrossOrigin(origins = "http://localhost:8080")  
public class AdministradorController {


    @Autowired
    private ActividadService actividadService;

    @DeleteMapping("/eliminarActividad/{idActividad}")
    public ResponseEntity<String> eliminarActividadAdmin(@PathVariable String idActividad) {
        actividadService.eliminarActividadComoAdmin(idActividad);
        return ResponseEntity.ok("Actividad eliminada correctamente");
    }
}
