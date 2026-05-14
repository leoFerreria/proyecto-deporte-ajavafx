package proyecto.deporte.javafx.model.dto;

import java.time.LocalDate;

public class MatchResultDTO {
    private String id;
    private String bookingId;
    private int player1Sets;
    private int player2Sets;
    private String winnerId;
    private LocalDate matchDate;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public int getPlayer1Sets() { return player1Sets; }
    public void setPlayer1Sets(int player1Sets) { this.player1Sets = player1Sets; }
    public int getPlayer2Sets() { return player2Sets; }
    public void setPlayer2Sets(int player2Sets) { this.player2Sets = player2Sets; }
    public String getWinnerId() { return winnerId; }
    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }
    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }
}