package proyecto.deporte.javafx.controller.users;

import proyecto.deporte.javafx.controller.BaseController;
import proyecto.deporte.javafx.model.dto.UserDTO;
import proyecto.deporte.javafx.model.enums.Role;
import proyecto.deporte.javafx.service.api.UserApiClient;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class UsersController extends BaseController {
    
    @FXML private TableView<UserDTO> usersTable;
    @FXML private TableColumn<UserDTO, String> colId;
    @FXML private TableColumn<UserDTO, String> colName;
    @FXML private TableColumn<UserDTO, String> colEmail;
    @FXML private TableColumn<UserDTO, Integer> colAge;
    @FXML private TableColumn<UserDTO, String> colGender;
    @FXML private TableColumn<UserDTO, String> colLevel;
    @FXML private TableColumn<UserDTO, Role> colRole;
    @FXML private TableColumn<UserDTO, Void> colActions;
    @FXML private TextField searchField;
    @FXML private ProgressIndicator loadingIndicator;
    
    private final UserApiClient userApi = new UserApiClient();
    private ObservableList<UserDTO> usersList = FXCollections.observableArrayList();
    private FilteredList<UserDTO> filteredUsers;
    
    @FXML
    public void initialize() {
        setupTableColumns();
        setupSearchFilter();
        loadUsers();
    }
    
    private void setupTableColumns() {
        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        colAge.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getAge()));
        colGender.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGender()));
        colLevel.setCellValueFactory(cell -> new SimpleStringProperty(
            cell.getValue().getLevel() != null ? cell.getValue().getLevel().name() : ""
        ));
        colRole.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getRole()));
        colActions.setCellFactory(createActionCellFactory());
    }
    
    private Callback<TableColumn<UserDTO, Void>, TableCell<UserDTO, Void>> createActionCellFactory() {
        return param -> new TableCell<>() {
            private final Button btnPromote = new Button("-> Admin");
            private final Button btnDelete = new Button("Eliminar");
            
            {
                btnPromote.setOnAction(e -> promoteToAdmin(getTableView().getItems().get(getIndex())));
                btnDelete.setOnAction(e -> deleteUser(getTableView().getItems().get(getIndex())));
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    UserDTO user = getTableView().getItems().get(getIndex());
                    HBox box = new HBox(5);
                    if (user.getRole() == Role.USER) {
                        box.getChildren().add(btnPromote);
                    }
                    box.getChildren().add(btnDelete);
                    setGraphic(box);
                }
            }
        };
    }
    
    private void setupSearchFilter() {
        filteredUsers = new FilteredList<>(usersList, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredUsers.setPredicate(user -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String lower = newVal.toLowerCase();
                return user.getEmail().toLowerCase().contains(lower) ||
                       (user.getName() != null && user.getName().toLowerCase().contains(lower));
            });
        });
        usersTable.setItems(filteredUsers);
    }
    
    private void loadUsers() {
        loadingIndicator.setVisible(true);
        executeAsync(
            userApi.getAllUsers(),
            users -> {
                usersList.setAll(users);
                loadingIndicator.setVisible(false);
            },
            ex -> {
                loadingIndicator.setVisible(false);
                showError("Error", "No se pudieron cargar los usuarios: " + ex.getMessage());
            }
        );
    }
    
    private void promoteToAdmin(UserDTO user) {
        if (!confirmAction("Confirmar", "Promover a " + user.getEmail() + " a ADMIN?")) return;
        user.setRole(Role.ADMIN);
        executeAsync(
            userApi.updateUser(user.getId(), user),
            updated -> {
                showInfo("Exito", "Usuario promovido a administrador");
                loadUsers();
            },
            ex -> showError("Error", "No se pudo actualizar el rol: " + ex.getMessage())
        );
    }
    
    private void deleteUser(UserDTO user) {
        if (!confirmAction("Confirmar eliminacion", "Eliminar permanentemente a " + user.getEmail() + "?")) return;
        executeAsync(
            userApi.deleteUser(user.getId()),
            v -> {
                showInfo("Eliminado", "Usuario eliminado correctamente");
                loadUsers();
            },
            ex -> showError("Error", "No se pudo eliminar: " + ex.getMessage())
        );
    }
}