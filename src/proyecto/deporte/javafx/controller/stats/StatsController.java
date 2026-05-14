package proyecto.deporte.javafx.controller.stats;

import proyecto.deporte.javafx.controller.BaseController;
import proyecto.deporte.javafx.service.api.StatsApiClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StatsController extends BaseController {
    
    @FXML private PieChart courtOccupancyChart;
    @FXML private BarChart<String, Number> sportUsageChart;
    @FXML private LineChart<String, Number> monthlyEvolutionChart;
    @FXML private BarChart<String, Number> topPlayersChart;
    @FXML private TableView<Map<String, Object>> rankingTable;
    @FXML private TableColumn<Map<String, Object>, Integer> colRank;
    @FXML private TableColumn<Map<String, Object>, String> colPlayerName;
    @FXML private TableColumn<Map<String, Object>, Integer> colWins;
    @FXML private TableColumn<Map<String, Object>, Integer> colLosses;
    @FXML private TableColumn<Map<String, Object>, Double> colWinRate;
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private ComboBox<String> sportFilter;
    @FXML private ProgressIndicator loadingIndicator;
    
    private final StatsApiClient statsApi = new StatsApiClient();
    
    @FXML
    public void initialize() {
        setupCharts();
        setupRankingTable();
        setupFilters();
        loadAllStats();
    }
    
    private void setupCharts() {
        sportUsageChart.getXAxis().setLabel("Deporte");
        sportUsageChart.getYAxis().setLabel("Reservas");
        monthlyEvolutionChart.getXAxis().setLabel("Mes");
        monthlyEvolutionChart.getYAxis().setLabel("Reservas");
        topPlayersChart.getXAxis().setLabel("Jugador");
        topPlayersChart.getYAxis().setLabel("Victorias");
    }
    
    @SuppressWarnings("unchecked")
    private void setupRankingTable() {
        colRank.setCellValueFactory(new PropertyValueFactory<>("position"));
        colPlayerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        colWins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        colLosses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        colWinRate.setCellValueFactory(new PropertyValueFactory<>("winRate"));
    }
    
    private void setupFilters() {
        fromDate.setValue(LocalDate.now().minusMonths(6));
        toDate.setValue(LocalDate.now());
        sportFilter.setItems(FXCollections.observableArrayList("Todos", "Padel", "Tenis", "Squash"));
        sportFilter.setValue("Todos");
    }
    
    private void loadAllStats() {
        loadingIndicator.setVisible(true);
        
        var occupancy = statsApi.getCourtOccupancy();
        var sportUsage = statsApi.getSportUsage();
        var monthly = statsApi.getMonthlyEvolution();
        var topPlayers = statsApi.getTopPlayers(10);
        var ranking = statsApi.getGlobalRanking();
        
        java.util.concurrent.CompletableFuture.allOf(occupancy, sportUsage, monthly, topPlayers, ranking)
            .thenRun(() -> Platform.runLater(() -> {
                try {
                    updateCourtOccupancyChart(occupancy.join());
                    updateSportUsageChart(sportUsage.join());
                    updateMonthlyChart(monthly.join());
                    updateTopPlayersChart(topPlayers.join());
                    rankingTable.getItems().setAll(ranking.join());
                } catch (Exception e) {
                    showError("Error", "Error cargando estadisticas: " + e.getMessage());
                }
                loadingIndicator.setVisible(false);
            }))
            .exceptionally(ex -> {
                Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    showError("Error", "Error de conexion con el servidor");
                });
                return null;
            });
    }
    
    private void updateCourtOccupancyChart(Map<String, Double> data) {
        courtOccupancyChart.getData().clear();
        data.forEach((court, pct) -> 
            courtOccupancyChart.getData().add(new PieChart.Data(court + " (" + pct + "%)", pct))
        );
    }
    
    private void updateSportUsageChart(Map<String, Long> data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Uso por deporte");
        data.forEach((sport, count) -> series.getData().add(new XYChart.Data<>(sport, count)));
        sportUsageChart.getData().clear();
        sportUsageChart.getData().add(series);
    }
    
    private void updateMonthlyChart(Map<String, Long> data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Reservas mensuales");
        data.entrySet().stream().sorted(Map.Entry.comparingByKey())
            .forEach(e -> series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue())));
        monthlyEvolutionChart.getData().clear();
        monthlyEvolutionChart.getData().add(series);
    }
    
    @SuppressWarnings("unchecked")
    private void updateTopPlayersChart(List<Map<String, Object>> players) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top 10 - Victorias");
        for (Map<String, Object> p : players) {
            String name = (String) p.get("name");
            Number wins = (Number) p.get("wins");
            series.getData().add(new XYChart.Data<>(name, wins));
        }
        topPlayersChart.getData().clear();
        topPlayersChart.getData().add(series);
    }
    
    @FXML
    private void handleRefresh() {
        loadAllStats();
    }
}