package proyecto.deporte.javafx.model.dto;

public class AuthResponseDTO {
    private String token;
    private UserDTO user;
    // getters y setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
}