package proyecto.deporte.javafx.model.dto;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long id;
    private Long userId;
    private String courtName;
    private String sport;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCourtName() { return courtName; }
    public void setCourtName(String courtName) { this.courtName = courtName; }
    public String getSport() { return sport; }
    public void setSport(String sport) { this.sport = sport; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}