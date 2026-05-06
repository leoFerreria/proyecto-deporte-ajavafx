package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.model.dto.BookingDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BookingApiClient extends ApiClient {
    public CompletableFuture<List<BookingDTO>> getAllBookings() {
        HttpRequest request = buildRequest(""/bookings/all"").GET().build();
        return sendAsync(request, new TypeReference<List<BookingDTO>>() {});
    }

    public CompletableFuture<Void> cancelBooking(Long id) {
        HttpRequest request = buildRequest(""/bookings/"" + id).DELETE().build();
        return sendAsync(request, String.class).thenApply(s -> null);
    }
}