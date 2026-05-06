package proyecto.deporte.javafx.model.dto;

import proyecto.deporte.javafx.model.enums.Role;

public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private Role role;
    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}