package isst.isst_group21.movemate_back.models;

import jakarta.persistence.*; 
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ORGANIZADOR") 
@Getter
@Setter
public class Organizador {
    
    //DNI puesto por el organizador
    @Id
    @Column(name = "ID_ORGANIZADOR", length = 9)
    private String idOrg;

    @Column(name = "NOMBRE",nullable = false)
    private String nombre;

    @Column(name = "APELLIDOS",nullable = false)
    private String apellidos;

    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Column(name = "NUM_TELEFONO",length = 15, nullable = false, unique = true)
    private String numTelefono;

    // Constructor vacío
    public Organizador() {
    }

    // Constructor completo
    public Organizador(String idOrg, String nombre, String apellidos, String email, String password, String numTelefono) {
        this.idOrg = idOrg;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.numTelefono = numTelefono;
    }

    // Getters y Setters
    public String getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(String idOrg) {
        this.idOrg = idOrg;
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

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getDniSinLetra() {
        if (idOrg != null && idOrg.length() > 0) {
            return idOrg.substring(0, idOrg.length() - 1);
        }
        return null;
    }

}
