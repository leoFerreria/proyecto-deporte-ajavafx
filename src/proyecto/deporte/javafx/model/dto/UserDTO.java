package proyecto.deporte.javafx.model.dto;

import proyecto.deporte.javafx.model.enums.Level;
import proyecto.deporte.javafx.model.enums.Role;
import java.time.LocalDate;

public class UserDTO {
    private String id;
    private String name;
    private String email;
    private int age;
    private LocalDate birthDate;
    private String gender;
    private Level level;
    private Role role;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}