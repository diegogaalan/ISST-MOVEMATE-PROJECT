package isst.isst_group21.movemate_back.services;

import isst.isst_group21.movemate_back.enums.Categorias;
import isst.isst_group21.movemate_back.DTO.ActividadDTO;
import isst.isst_group21.movemate_back.DTO.RegistroDTO;
import isst.isst_group21.movemate_back.models.Actividad;
import isst.isst_group21.movemate_back.models.Organizador;
import isst.isst_group21.movemate_back.repositories.OrganizadorRepository;
import isst.isst_group21.movemate_back.repositories.ReservaRepository;
import isst.isst_group21.movemate_back.repositories.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.util.Optional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrganizadorService {

    @Autowired
    private OrganizadorRepository organizadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ReservaRepository reservaRepository;


    @Autowired
    private EmailService emailService;

    public OrganizadorService(OrganizadorRepository organizadorRepository, ActividadRepository actividadRepository, PasswordEncoder passwordEncoder) {
        this.organizadorRepository = organizadorRepository;
        this.actividadRepository = actividadRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registro de organizador
    public Organizador registrarOrganizador(RegistroDTO dto) {
        if (organizadorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (organizadorRepository.findByNumTelefono(dto.getNumTelefono()).isPresent()) {
            throw new RuntimeException("El número de teléfono ya está registrado");
        }
        if (organizadorRepository.findById(dto.getId()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        if (dto.getPassword().length() < 8) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres");
        }

        Organizador organizador = new Organizador();
        organizador.setIdOrg(dto.getId());
        organizador.setNombre(dto.getNombre());
        organizador.setApellidos(dto.getApellidos());
        organizador.setEmail(dto.getEmail());
        organizador.setNumTelefono(dto.getNumTelefono());
        organizador.setPassword(passwordEncoder.encode(dto.getPassword()));

        Organizador guardado = organizadorRepository.save(organizador);

        //Enviar correo de bienvenida
        try{
            emailService.enviarBienvenida(organizador.getEmail(), organizador.getNombre());
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }

        return guardado;
    }

    //Crear una actividad
    public Actividad crearActividad(ActividadDTO dto, String idOrganizador) {
        Organizador organizador = organizadorRepository.findById(idOrganizador)
            .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
        Actividad actividad = new Actividad();
        actividad.setNombre(dto.getNombre());
        actividad.setCategoria(Categorias.valueOf(dto.getCategoria().toUpperCase()));
        actividad.setDescripcion(dto.getDescripcion());
        actividad.setFechaHora(LocalDateTime.parse(dto.getFechaHora()));
        actividad.setPrecio(dto.getPrecio());
        actividad.setAforoMaximo(dto.getAforoMaximo());
        actividad.setUbicacion(dto.getUbicacion());
        actividad.setLatitud(dto.getLat());
        actividad.setLongitud(dto.getLon());
        actividad.setConfirmada(false);
        actividad.setOrganizador(organizador);
        actividad.setIdActividad(Actividad.generarIdActividad(organizador));
        
        if (dto.getFotoBase64() != null && !dto.getFotoBase64().isEmpty()) {
            actividad.setFoto(dto.getFotoBase64().getBytes());
        } else {
            Categorias categoria = Categorias.valueOf(dto.getCategoria());
            actividad.setFoto(cargarImagenPorDefecto(categoria));
        }
        return actividadRepository.save(actividad);
    }

    private byte[] cargarImagenPorDefecto(Categorias categoria) {
        String ruta = "imagenes/por_defecto/" + categoria.getNombreArchivo();
        try {
            ClassPathResource imgResource = new ClassPathResource(ruta);
            return StreamUtils.copyToByteArray(imgResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la imagen predeterminada para la categoría: " + categoria, e);
        }
    }

    public Actividad actualizarActividad(String idActividad, ActividadDTO dto, String idOrg) {
        Optional<Actividad> optionalActividad = actividadRepository.findById(idActividad);
        if (!optionalActividad.isPresent()) {
            throw new RuntimeException("Actividad no encontrada");
        }
        Actividad actividad = optionalActividad.get();
        if (!actividad.getOrganizador().getIdOrg().equals(idOrg)) {
            throw new RuntimeException("No autorizado para modificar esta actividad");
        }
        actividad.setNombre(dto.getNombre());
        actividad.setCategoria(Categorias.valueOf(dto.getCategoria().toUpperCase()));
        actividad.setDescripcion(dto.getDescripcion());
        actividad.setFechaHora(LocalDateTime.parse(dto.getFechaHora()));
        actividad.setPrecio(dto.getPrecio());
        actividad.setAforoMaximo(dto.getAforoMaximo());
        actividad.setUbicacion(dto.getUbicacion());
        actividad.setLatitud(dto.getLat());
        actividad.setLongitud(dto.getLon());
        if (dto.getFotoBase64() != null && !dto.getFotoBase64().isEmpty()) {
            actividad.setFoto(dto.getFotoBase64().getBytes());
        } else {
            Categorias categoria = Categorias.valueOf(dto.getCategoria());
            actividad.setFoto(cargarImagenPorDefecto(categoria));
        }

        return actividadRepository.save(actividad);
    }

    public List<Actividad> obtenerActividadesPorOrganizador(String idOrg) {
        Optional<Organizador> organizadorOpt = organizadorRepository.findById(idOrg);
        if (organizadorOpt.isPresent()) {
            List<Actividad> actividades = actividadRepository.findByOrganizador_IdOrg(idOrg);
            for (Actividad actividad : actividades) {
                int count = reservaRepository.countByActividadAndCanceladaFalse(actividad);
                actividad.setNumReservas(count);
            }
            return actividades;
        } else {
            throw new RuntimeException("Organizador no encontrado");
        }

        
    }

    public Organizador verPerfil(String idOrg) {
        return organizadorRepository.findById(idOrg)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
    }

    public Organizador actualizarPerfil(String idOrg, RegistroDTO dto) {
        Organizador organizador = organizadorRepository.findById(idOrg)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
        if (organizadorRepository.findByEmail(dto.getEmail())
            .filter(u -> !u.getIdOrg().equals(idOrg)).isPresent()) {
            throw new RuntimeException("El email ya está registrado por otro organizador");
        }
        if (organizadorRepository.findByNumTelefono(dto.getNumTelefono())
            .filter(u -> !u.getIdOrg().equals(idOrg)).isPresent()) {
            throw new RuntimeException("El número de teléfono ya está registrado por otro organizador");
        }
        organizador.setNombre(dto.getNombre());
        organizador.setApellidos(dto.getApellidos());
        organizador.setEmail(dto.getEmail());
        organizador.setNumTelefono(dto.getNumTelefono());
        organizador.setPassword(passwordEncoder.encode(dto.getPassword()));
        return organizadorRepository.save(organizador);
    }

    public void borrarOrganizador(String idOrg) {
        Optional<Organizador> organizadorABorrar = organizadorRepository.findById(idOrg);
        if (organizadorABorrar.isPresent()) {
            organizadorRepository.delete(organizadorABorrar.get());
        } else {
            throw new RuntimeException("Organizador no encontrado");
        }
    }
}
