package proyecto.deporte.javafx.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class BaseController {

    protected void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    protected void showInfo(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    protected boolean confirmAction(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    protected <T> void executeAsync(CompletableFuture<T> future, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        future.thenAccept(result -> Platform.runLater(() -> onSuccess.accept(result)))
              .exceptionally(ex -> {
                  Platform.runLater(() -> onError.accept(ex));
                  return null;
              });
    }
}