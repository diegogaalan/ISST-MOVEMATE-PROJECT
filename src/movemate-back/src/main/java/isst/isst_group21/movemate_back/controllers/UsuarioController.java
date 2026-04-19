package isst.isst_group21.movemate_back.controllers;

import isst.isst_group21.movemate_back.DTO.RegistroDTO;
import isst.isst_group21.movemate_back.DTO.ReservaDTO;
import isst.isst_group21.movemate_back.models.Reserva;
import isst.isst_group21.movemate_back.models.Usuario;
import isst.isst_group21.movemate_back.services.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;


@RestController  
@RequestMapping("/usuarios")  
@CrossOrigin(origins = "http://localhost:8080")  
public class UsuarioController {

    @Autowired // Inyecta la dependencia
    private UsuarioService usuarioService;  //Contiene la logica de la app de los usuarios

    //Reservar actividad
    @PostMapping("/{idUsuario}/{idActividad}/reservar")
    public  ResponseEntity<String> reservarActividad(@PathVariable String idUsuario, @PathVariable String idActividad) {
        //return ResponseEntity.ok(usuarioService.reservarActividad(idUsuario, idActividad));
        usuarioService.reservarActividad(idUsuario, idActividad);
        return ResponseEntity.ok("Reserva realizada correctamente");
    }

    //Cancelar reserva
    @DeleteMapping("/{idUsuario}/{idReserva}/cancelarReserva")
    public ResponseEntity<String> cancelarReserva(@PathVariable Long idReserva, @PathVariable String idUsuario) {
        usuarioService.cancelarReserva( idReserva,idUsuario);
        return ResponseEntity.ok("Reserva cancelada correctamente");
    }

    //Ver reservas de un usuario
    @GetMapping("/{idUsuario}/reservas") 
    public ResponseEntity<List<ReservaDTO>> verReservas(@PathVariable String idUsuario) {
    List<Reserva> reservas = usuarioService.obtenerReservasUsuario(idUsuario);
    List<ReservaDTO> dtoList = reservas.stream()
        .map(ReservaDTO::new)
        .toList();
    return ResponseEntity.ok(dtoList);
        //return ResponseEntity.ok(usuarioService.obtenerReservasUsuario(idUsuario));
    }
    
    //Ver perfil
    @GetMapping("/profile/{idUsuario}")
    public ResponseEntity<Usuario> verPerfil(@PathVariable String idUsuario) {
        return ResponseEntity.ok(usuarioService.verPerfil(idUsuario));
    }

    //Actualizar perfil
    @PutMapping("profile/{idUsuario}")
    public ResponseEntity<?> actualizarPerfil(@PathVariable String idUsuario, @Valid @RequestBody RegistroDTO usuarioDTO,  BindingResult result) {
        if (result.hasErrors()) {
            // Devuelve todos los errores de validación
            List<String> errores = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(usuarioService.actualizarPerfil(idUsuario, usuarioDTO));
    }
    
    // Borrar perfil de usuario
    @DeleteMapping("/profile/{idUsuario}")
    public ResponseEntity<String> borrarUsuario(@PathVariable String idUsuario) {
        usuarioService.borrarUsuario(idUsuario);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
    
    
}