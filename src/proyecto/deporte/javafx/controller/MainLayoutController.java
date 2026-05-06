package proyecto.deporte.javafx.controller;

import proyecto.deporte.javafx.config.SessionManager;
import proyecto.deporte.javafx.model.dto.AuthResponseDTO;
import proyecto.deporte.javafx.model.enums.Role;
import proyecto.deporte.javafx.service.api.AuthApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends BaseController {
    
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private ProgressIndicator loadingIndicator;

    private final AuthApiClient authClient = new AuthApiClient();

    @FXML
    public void initialize() {
        loadingIndicator.setVisible(false);
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Completa todos los campos");
            errorLabel.setVisible(true);
            return;
        }

        loadingIndicator.setVisible(true);
        errorLabel.setVisible(false);

        authClient.login(email, password)
            .thenAccept(this::onLoginSuccess)
            .exceptionally(ex -> {
                Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    errorLabel.setText("Credenciales incorrectas o error de conexion");
                    errorLabel.setVisible(true);
                });
                return null;
            });
    }

    private void onLoginSuccess(AuthResponseDTO auth) {
        if (auth.getUser().getRole() != Role.ADMIN) {
            Platform.runLater(() -> {
                loadingIndicator.setVisible(false);
                errorLabel.setText("Acceso denegado: Solo administradores");
                errorLabel.setVisible(true);
            });
            return;
        }

        SessionManager.getInstance().setSession(auth.getToken(), auth.getUser());

        Platform.runLater(() -> {
            try {
                loadMainWindow();
            } catch (Exception e) {
                showError("Error", "No se pudo cargar la aplicacion");
            }
        });
    }

    private void loadMainWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/deporte/javafx/view/main-layout.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 900));
        stage.setTitle("SportCenter Admin - Panel de Administracion");
        stage.setMaximized(true);
        stage.show();
    }
}