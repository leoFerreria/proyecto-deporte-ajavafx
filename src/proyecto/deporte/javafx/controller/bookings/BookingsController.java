package proyecto.deporte.javafx.controller.bookings;

import proyecto.deporte.javafx.controller.BaseController;
import proyecto.deporte.javafx.model.dto.BookingDTO;
import proyecto.deporte.javafx.service.api.BookingApiClient;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.time.format.DateTimeFormatter;

public class BookingsController extends BaseController {
    
    @FXML private TableView<BookingDTO> bookingsTable;
    @FXML private TableColumn<BookingDTO, String> colId;
    @FXML private TableColumn<BookingDTO, String> colDateTime;
    @FXML private TableColumn<BookingDTO, String> colCourt;
    @FXML private TableColumn<BookingDTO, String> colPlayer1;
    @FXML private TableColumn<BookingDTO, String> colPlayer2;
    @FXML private TableColumn<BookingDTO, Boolean> colFull;
    @FXML private TableColumn<BookingDTO, Void> colActions;
    @FXML private ProgressIndicator loadingIndicator;
    @FXML private Label infoLabel;
    
    private final BookingApiClient bookingApi = new BookingApiClient();
    private ObservableList<BookingDTO> bookingsList = FXCollections.observableArrayList();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    @FXML
    public void initialize() {
        setupTableColumns();
        loadBookings();
    }
    
    private void setupTableColumns() {
        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colDateTime.setCellValueFactory(cell -> new SimpleStringProperty(
            cell.getValue().getDateTime() != null ? cell.getValue().getDateTime().format(formatter) : ""
        ));
        colCourt.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCourtName()));
        colPlayer1.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPlayer1Id()));
        colPlayer2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPlayer2Id()));
        colFull.setCellValueFactory(cell -> new SimpleBooleanProperty(cell.getValue().isFull()));
        colActions.setCellFactory(createActionCellFactory());
    }
    
    private Callback<TableColumn<BookingDTO, Void>, TableCell<BookingDTO, Void>> createActionCellFactory() {
        return param -> new TableCell<>() {
            private final Button btnCancel = new Button("Cancelar");
            
            {
                btnCancel.setOnAction(e -> cancelBooking(getTableView().getItems().get(getIndex())));
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(5, btnCancel));
            }
        };
    }
    
    private void loadBookings() {
        loadingIndicator.setVisible(true);
        infoLabel.setText("");
        executeAsync(
            bookingApi.getAllBookings(),
            bookings -> {
                bookingsList.setAll(bookings);
                bookingsTable.setItems(bookingsList);
                loadingIndicator.setVisible(false);
            },
            ex -> {
                loadingIndicator.setVisible(false);
                infoLabel.setText("ERROR: El endpoint GET /api/bookings no existe en el backend. Pide a tu companero que lo anada.");
                showError("Error", "No se pudieron cargar las reservas. Endpoint no disponible.");
            }
        );
    }
    
    private void cancelBooking(BookingDTO booking) {
        if (!confirmAction("Cancelar reserva", "Cancelar la reserva #" + booking.getId() + "?")) return;
        executeAsync(
            bookingApi.cancelBooking(booking.getId()),
            v -> {
                showInfo("Cancelada", "Reserva cancelada correctamente");
                loadBookings();
            },
            ex -> showError("Error", "No se pudo cancelar: " + ex.getMessage())
        );
    }
}