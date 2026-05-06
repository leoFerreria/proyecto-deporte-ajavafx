package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.config.ApiConfig;
import proyecto.deporte.javafx.model.dto.AuthResponseDTO;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;

public class AuthApiClient extends ApiClient {
    
    public CompletableFuture<AuthResponseDTO> login(String email, String password) {
        try {
            String json = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
                
            return sendAsync(request, AuthResponseDTO.class);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}