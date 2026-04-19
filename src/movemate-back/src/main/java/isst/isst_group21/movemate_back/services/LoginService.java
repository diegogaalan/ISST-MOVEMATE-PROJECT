package isst.isst_group21.movemate_back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import isst.isst_group21.movemate_back.DTO.LoginDTO;
import isst.isst_group21.movemate_back.DTO.LoginResponseDTO;
import isst.isst_group21.movemate_back.config.JwtUtil;
import isst.isst_group21.movemate_back.models.Usuario;
import isst.isst_group21.movemate_back.models.Organizador;
import isst.isst_group21.movemate_back.repositories.OrganizadorRepository;
import isst.isst_group21.movemate_back.repositories.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrganizadorRepository organizadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Login de usuario
    public LoginResponseDTO login(LoginDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(dto.getEmail());
        Optional<Organizador> organizador = organizadorRepository.findByEmail(dto.getEmail());

        if (usuario.isPresent()) {
            System.out.println("Usuario encontrado: " + usuario.get().getEmail());
            System.out.println("Password en DB: " + usuario.get().getPassword());
        } else {
            System.out.println("No se encontró usuario con ese email.");
        }
        
        // Hacemos lo mismo para organizador:
        if (organizador.isPresent()) {
            System.out.println("Organizador encontrado: " + organizador.get().getEmail());
            System.out.println("Password en DB: " + organizador.get().getPassword());
        } else {
            System.out.println("No se encontró organizador con ese email.");
        }


        if (usuario.isPresent() && passwordEncoder.matches(dto.getPassword(), usuario.get().getPassword())) {
            String token = jwtUtil.generateToken(usuario.get().getEmail(), "usuario", usuario.get().getIdUsuario()); //Generamos el token
            return new LoginResponseDTO(token, "usuario", usuario.get().getIdUsuario());

        }else if (organizador.isPresent() && passwordEncoder.matches(dto.getPassword(), organizador.get().getPassword())) {
            String token = jwtUtil.generateToken(organizador.get().getEmail(), "organizador", organizador.get().getIdOrg()); //Generamos el token
            return new LoginResponseDTO(token, "organizador", organizador.get().getIdOrg());
            
        } else {
            throw new RuntimeException("Credenciales incorrectas");
        }

    }
}
