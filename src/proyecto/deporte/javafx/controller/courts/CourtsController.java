package proyecto.deporte.javafx.controller.courts;

import proyecto.deporte.javafx.controller.BaseController;
import proyecto.deporte.javafx.model.dto.CourtDTO;
import proyecto.deporte.javafx.service.api.CourtApiClient;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class CourtsController extends BaseController {
    
    @FXML private TableView<CourtDTO> courtsTable;
    @FXML private TableColumn<CourtDTO, Long> colId;
    @FXML private TableColumn<CourtDTO, String> colName;
    @FXML private TableColumn<CourtDTO, String> colSport;
    @FXML private TableColumn<CourtDTO, String> colStatus;
    @FXML private TableColumn<CourtDTO, Void> colActions;
    @FXML private ProgressIndicator loadingIndicator;
    
    private final CourtApiClient courtApi = new CourtApiClient();
    private ObservableList<CourtDTO> courtsList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        setupTableColumns();
        loadCourts();
    }
    
    private void setupTableColumns() {
        colId.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId()));
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colSport.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSport()));
        colStatus.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus()));
        colActions.setCellFactory(createActionCellFactory());
    }
    
    private Callback<TableColumn<CourtDTO, Void>, TableCell<CourtDTO, Void>> createActionCellFactory() {
        return param -> new TableCell<>() {
            private final Button btnStatus = new Button("Cambiar Estado");
            
            {
                btnStatus.setOnAction(e -> toggleStatus(getTableView().getItems().get(getIndex())));
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(5, btnStatus));
            }
        };
    }
    
    private void loadCourts() {
        loadingIndicator.setVisible(true);
        executeAsync(
            courtApi.getAllCourts(),
            courts -> {
                courtsList.setAll(courts);
                courtsTable.setItems(courtsList);
                loadingIndicator.setVisible(false);
            },
            ex -> {
                loadingIndicator.setVisible(false);
                showError("Error", "No se pudieron cargar las pistas: " + ex.getMessage());
            }
        );
    }
    
    private void toggleStatus(CourtDTO court) {
        String current = court.getStatus();
        String next = "AVAILABLE".equals(current) ? "MAINTENANCE" : "AVAILABLE";
        court.setStatus(next);
        showInfo("Info", "Estado cambiado a " + next + " (necesitas endpoint PUT en backend para persistir)");
        courtsTable.refresh();
    }
}