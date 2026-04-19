package isst.isst_group21.movemate_back.services;

import isst.isst_group21.movemate_back.DTO.RegistroDTO;
import isst.isst_group21.movemate_back.models.Actividad;
import isst.isst_group21.movemate_back.models.Reserva;
import isst.isst_group21.movemate_back.models.Usuario;
import isst.isst_group21.movemate_back.repositories.ReservaRepository;
import isst.isst_group21.movemate_back.repositories.UsuarioRepository;
import isst.isst_group21.movemate_back.repositories.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, ReservaRepository reservaRepository, ActividadRepository actividadRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.reservaRepository = reservaRepository;
        this.actividadRepository = actividadRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registro de usuario
    public Usuario registrarUsuario(RegistroDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (usuarioRepository.findByNumTelefono(dto.getNumTelefono()).isPresent()) {
            throw new RuntimeException("El número de teléfono ya está registrado");
        }
        if (usuarioRepository.findById(dto.getId()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }
        if (dto.getPassword().length() < 8) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setNumTelefono(dto.getNumTelefono());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        //Enviar correo de bienvenida
        try {
            emailService.enviarBienvenida(usuario.getEmail(), usuario.getNombre());
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }
        

        return usuarioGuardado;
    }


    public Reserva reservarActividad(String idUsuario, String idActividad) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        Optional<Actividad> actividadOptional = actividadRepository.findById(idActividad);
        if (usuarioOptional.isPresent() && actividadOptional.isPresent()) {
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuarioOptional.get());
            reserva.setActividad(actividadOptional.get());
            //reserva.setFechaReserva(LocalDateTime.now());
            reserva.setConfirmada(false);
            reserva.setCancelada(false);
            reserva.setEnProceso(true);
            return reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Usuario o actividad no encontrados");
        }
    }

    public List<Reserva> obtenerReservasUsuario(String idUsuario) {
        return reservaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public void cancelarReserva(Long idReserva, String idUsuario) {
        usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Reserva reserva = reservaRepository.findByIdReservaAndUsuario_IdUsuario(idReserva, idUsuario)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada o no pertenece a este usuario"));
        if (reserva.isCancelada()) {
            throw new RuntimeException("La reserva ya está cancelada");
        }
        reservaRepository.delete(reserva);
        /*reserva.setCancelada(true);
        reservaRepository.save(reserva);*/
    }

    public Usuario verPerfil(String idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario actualizarPerfil(String idUsuario, RegistroDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuarioRepository.findByEmail(dto.getEmail())
            .filter(u -> !u.getIdUsuario().equals(idUsuario)).isPresent()) {
            throw new RuntimeException("El email ya está registrado por otro usuario");
        }
        if (usuarioRepository.findByNumTelefono(dto.getNumTelefono())
            .filter(u -> !u.getIdUsuario().equals(idUsuario)).isPresent()) {
            throw new RuntimeException("El número de teléfono ya está registrado por otro usuario");
        }
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setNumTelefono(dto.getNumTelefono());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public void borrarUsuario(String idUsuario) {
        Optional<Usuario> usuarioABorrar = usuarioRepository.findById(idUsuario);
        if (usuarioABorrar.isPresent()) {
            usuarioRepository.delete(usuarioABorrar.get());
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}
