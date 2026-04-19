package isst.isst_group21.movemate_back.models;

import jakarta.persistence.*; 
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "USUARIO") 
@Getter
@Setter
public class Usuario {
    //nombre de usuario puesto por el propio usuario
    @Id
    @Column(name = "ID_USUARIO", nullable = false, unique = true)
    private String idUsuario;

    //atributos de la tabla
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDOS",nullable = false)
    private String apellidos;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NUM_TELEFONO", length = 15, unique = true)
    private String numTelefono;

    // Constructor vacío
    public Usuario() {
    }
    // Constructor completo
    public Usuario(String idUsuario, String nombre, String apellidos, String email, String password, String numTelefono) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.numTelefono = numTelefono;
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono){
    this.numTelefono = numTelefono;
    }
}
