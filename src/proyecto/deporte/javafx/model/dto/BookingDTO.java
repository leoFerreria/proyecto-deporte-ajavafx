package proyecto.deporte.javafx.model.dto;

import java.time.LocalDateTime;

public class BookingDTO {
    private String id;
    private LocalDateTime dateTime;
    private String courtName;
    private String player1Id;
    private String player2Id;
    private boolean isFull;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getCourtName() { return courtName; }
    public void setCourtName(String courtName) { this.courtName = courtName; }
    public String getPlayer1Id() { return player1Id; }
    public void setPlayer1Id(String player1Id) { this.player1Id = player1Id; }
    public String getPlayer2Id() { return player2Id; }
    public void setPlayer2Id(String player2Id) { this.player2Id = player2Id; }
    public boolean isFull() { return isFull; }
    public void setFull(boolean full) { isFull = full; }
}