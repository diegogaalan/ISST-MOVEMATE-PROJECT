package isst.isst_group21.movemate_back.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroDTO {
    @NotBlank(message = "Este campo no puede estar vacio")
    private String id;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotBlank(message = "Los apellidos no puede estar vacío")
    private String apellidos;
    
    @Email(message = "El email no es válido")
    @NotBlank(message = "El correo no puede estar vacío")
    private String email;

    @NotBlank(message = "El número de teléfono no puede estar vacio")
    @Size(min = 9, max = 9,  message = "El número de teléfono debe tener 9 caracteres")
    private String numTelefono;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    public RegistroDTO() {
    }

    //Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //toString
    @Override
    public String toString() {
        return "UsuarioRegistroDTO{" +
                "idUsuario='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
