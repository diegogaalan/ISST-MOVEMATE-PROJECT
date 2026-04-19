package isst.isst_group21.movemate_back.DTO;

public class LoginResponseDTO {
    private String token;
    private String role;
    private String id;

    public LoginResponseDTO(String token, String role, String id) {
        this.token = token;
        this.role = role;
        this.id = id;

    }

    // Getters y setters
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
