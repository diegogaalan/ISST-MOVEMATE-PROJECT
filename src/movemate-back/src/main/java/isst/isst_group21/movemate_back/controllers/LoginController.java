package isst.isst_group21.movemate_back.controllers;

import isst.isst_group21.movemate_back.DTO.LoginDTO;
import isst.isst_group21.movemate_back.DTO.LoginResponseDTO;
import isst.isst_group21.movemate_back.services.LoginService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@RequestMapping("/login") 
@CrossOrigin(origins = "http://localhost:8080")  
public class LoginController {

    @Autowired // Inyecta la dependencia
    private LoginService loginService;  //Contiene la logica de la app de los usuarios

    //Login
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            // Devuelve todos los errores de validación
            List<String> errores = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
            return ResponseEntity.badRequest().body(errores);
        }
    LoginResponseDTO response = loginService.login(loginDTO);
    return ResponseEntity.ok(response); // Enviamos token + redirect
}

}
