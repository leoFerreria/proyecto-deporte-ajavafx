package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.model.dto.CourtDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CourtApiClient extends ApiClient {
    public CompletableFuture<List<CourtDTO>> getAllCourts() {
        HttpRequest request = buildRequest(""/courts"").GET().build();
        return sendAsync(request, new TypeReference<List<CourtDTO>>() {});
    }
}