package isst.isst_group21.movemate_back.models;

import jakarta.persistence.*; 
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ADMINISTRADOR") 
@Getter
@Setter
public class Administrador {
    // Será el DNI puesto por el organizador
    @Id
    @Column(name = "ID_ADMIN", length = 9)
    private String idAdmin;

    @Column(name = "NOMBRE",nullable = false)
    private String nombre;

    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    // Constructor vacío
    public Administrador() {
    }

    // Constructor completo
    public Administrador(String idAdmin, String nombre, String email, String password) {
        this.idAdmin = idAdmin;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    } 

    // Getters y Setters
    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

}
