package proyecto.deporte.javafx.service.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import proyecto.deporte.javafx.config.ApiConfig;
import proyecto.deporte.javafx.config.SessionManager;
import proyecto.deporte.javafx.model.exception.ApiException;
import proyecto.deporte.javafx.model.exception.UnauthorizedException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public abstract class ApiClient {
    
    protected final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    protected HttpRequest.Builder buildRequest(String path) {
        return HttpRequest.newBuilder()
            .uri(URI.create(ApiConfig.BASE_URL + path))
            .timeout(Duration.ofSeconds(ApiConfig.REQUEST_TIMEOUT_SECONDS))
            .header("Authorization", SessionManager.getInstance().getAuthHeader())
            .header("Content-Type", "application/json")
            .header("Accept", "application/json");
    }

    protected <T> CompletableFuture<T> sendAsync(HttpRequest request, Class<T> responseType) {
        return HttpClientProvider.get()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(this::handleResponse)
            .thenApply(body -> {
                try {
                    return mapper.readValue(body, responseType);
                } catch (Exception e) {
                    throw new ApiException("Error parseando JSON: " + e.getMessage());
                }
            });
    }

    protected <T> CompletableFuture<T> sendAsync(HttpRequest request, TypeReference<T> typeRef) {
        return HttpClientProvider.get()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(this::handleResponse)
            .thenApply(body -> {
                try {
                    return mapper.readValue(body, typeRef);
                } catch (Exception e) {
                    throw new ApiException("Error parseando JSON: " + e.getMessage());
                }
            });
    }

    private String handleResponse(HttpResponse<String> response) {
        int status = response.statusCode();
        
        if (status == 401 || status == 403) {
            SessionManager.getInstance().clear();
            throw new UnauthorizedException("Sesion expirada o acceso denegado");
        }
        if (status >= 400) {
            throw new ApiException("Error API: " + status + " - " + response.body());
        }
        return response.body();
    }
}