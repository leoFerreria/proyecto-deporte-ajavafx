package proyecto.deporte.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainLayoutController {
    
    @FXML private BorderPane mainLayout;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        loadView("dashboard/dashboard.fxml");
    }

    @FXML private void showDashboard() { loadView("dashboard/dashboard.fxml"); }
    @FXML private void showUsers()     { loadView("users/users.fxml"); }
    @FXML private void showBookings()  { loadView("bookings/bookings.fxml"); }
    @FXML private void showStats()     { loadView("stats/stats.fxml"); }

    private void loadView(String fxmlPath) {
        try {
            Node node = FXMLLoader.load(
                getClass().getResource("/proyecto/deporte/javafx/view/" + fxmlPath)
            );
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}