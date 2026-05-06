package proyecto.deporte.javafx.model.dto;

public class CourtDTO {
    private Long id;
    private String name;
    private String sport;
    private String status;
    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSport() { return sport; }
    public void setSport(String sport) { this.sport = sport; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}