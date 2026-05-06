package proyecto.deporte.javafx.service.api;

import proyecto.deporte.javafx.model.dto.stats.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class StatsApiClient extends ApiClient {
    public CompletableFuture<Map<String, Double>> getCourtOccupancy() {
        HttpRequest request = buildRequest(""/stats/court-occupancy"").GET().build();
        return sendAsync(request, new TypeReference<Map<String, Double>>() {});
    }

    public CompletableFuture<Map<String, Long>> getSportUsage() {
        HttpRequest request = buildRequest(""/stats/sport-usage"").GET().build();
        return sendAsync(request, new TypeReference<Map<String, Long>>() {});
    }

    public CompletableFuture<Map<String, Long>> getMonthlyEvolution() {
        HttpRequest request = buildRequest(""/stats/monthly-evolution"").GET().build();
        return sendAsync(request, new TypeReference<Map<String, Long>>() {});
    }

    public CompletableFuture<List<TopPlayerDTO>> getTopPlayers(int limit) {
        HttpRequest request = buildRequest(""/stats/top-players?limit=""+limit).GET().build();
        return sendAsync(request, new TypeReference<List<TopPlayerDTO>>() {});
    }

    public CompletableFuture<List<RankingDTO>> getGlobalRanking() {
        HttpRequest request = buildRequest(""/stats/global-ranking"").GET().build();
        return sendAsync(request, new TypeReference<List<RankingDTO>>() {});
    }
}