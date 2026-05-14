package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.model.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserApiClient extends ApiClient {
    
    public CompletableFuture<List<UserDTO>> getAllUsers() {
        HttpRequest request = buildRequest("/users").GET().build();
        return sendAsync(request, new TypeReference<List<UserDTO>>() {});
    }
    
    public CompletableFuture<Void> updateUser(String id, UserDTO user) {
        try {
            String json = mapper.writeValueAsString(user);
            HttpRequest request = buildRequest("/users/update/" + id)
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
            return sendAsync(request, String.class).thenApply(s -> null);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
    
    public CompletableFuture<Void> deleteUser(String id) {
        HttpRequest request = buildRequest("/users/delete/" + id).DELETE().build();
        return sendAsync(request, String.class).thenApply(s -> null);
    }
}