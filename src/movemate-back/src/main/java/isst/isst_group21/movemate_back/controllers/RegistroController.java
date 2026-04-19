package isst.isst_group21.movemate_back.controllers;

import isst.isst_group21.movemate_back.DTO.RegistroDTO;
import isst.isst_group21.movemate_back.services.UsuarioService;
import isst.isst_group21.movemate_back.services.OrganizadorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;


@RestController  
@RequestMapping("/registro")  
@CrossOrigin(origins = "http://localhost:8080")  
public class RegistroController {
    
    @Autowired // Inyecta la dependencia
    private UsuarioService usuarioService;  //Contiene la logica de la app de los usuarios

    @Autowired
    private OrganizadorService organizadorService;
    
    //Regitro usuario
    @PostMapping("/usuario")  // Rsponde a peticiones POST
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroDTO registroDTO,  BindingResult result) {
        if (result.hasErrors()) {
            // Devuelve todos los errores de validación
            List<String> errores = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(usuarioService.registrarUsuario(registroDTO));
    }

    //Regitro organizador (/registro/organizador)
    @PostMapping("/organizador")  //Responde a peticiones POST
    public ResponseEntity<?> registrarOrganizador(@Valid @RequestBody RegistroDTO registroDTO,  BindingResult result) {
        if (result.hasErrors()) {
            // Devuelve todos los errores de validación
            List<String> errores = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(organizadorService.registrarOrganizador(registroDTO));
    }
}
