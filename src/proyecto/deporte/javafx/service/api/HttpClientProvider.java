package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.config.ApiConfig;
import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientProvider {
    private static final HttpClient CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(ApiConfig.CONNECT_TIMEOUT_SECONDS))
        .build();

    public static HttpClient get() { return CLIENT; }
}