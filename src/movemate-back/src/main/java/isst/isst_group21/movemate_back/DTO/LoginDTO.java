package isst.isst_group21.movemate_back.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class LoginDTO {

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    private boolean esOrganizador;

    public LoginDTO() {
    }

    // Getters y setters
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

    public boolean getEsOrganizador() {
        return esOrganizador;
    }

    public void setEsOrganizador(boolean esOrganizador) {
        this.esOrganizador = esOrganizador;
    }


    @Override
    public String toString() {
        return "UsuarioLoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", esOrganizador=" + esOrganizador +
                '}';
    }
}
