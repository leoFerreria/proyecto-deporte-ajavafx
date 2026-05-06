package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.model.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserApiClient extends ApiClient {
    public CompletableFuture<List<UserDTO>> getAllUsers() {
        HttpRequest request = buildRequest(""/users"").GET().build();
        return sendAsync(request, new TypeReference<List<UserDTO>>() {});
    }
}