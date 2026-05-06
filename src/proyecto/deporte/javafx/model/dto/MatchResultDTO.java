package proyecto.deporte.javafx.model.dto;

public class MatchResultDTO {
    private Long id;
    private Long bookingId;
    private Long winnerId;
    private Long loserId;
    private Integer setsWon;
    private Integer setsLost;
    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public Long getWinnerId() { return winnerId; }
    public void setWinnerId(Long winnerId) { this.winnerId = winnerId; }
    public Long getLoserId() { return loserId; }
    public void setLoserId(Long loserId) { this.loserId = loserId; }
    public Integer getSetsWon() { return setsWon; }
    public void setSetsWon(Integer setsWon) { this.setsWon = setsWon; }
    public Integer getSetsLost() { return setsLost; }
    public void setSetsLost(Integer setsLost) { this.setsLost = setsLost; }
}