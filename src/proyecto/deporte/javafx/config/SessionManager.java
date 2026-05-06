package proyecto.deporte.javafx.config;

import proyecto.deporte.javafx.model.dto.UserDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionManager {
    
    private static final SessionManager INSTANCE = new SessionManager();
    
    private final StringProperty token = new SimpleStringProperty();
    private final ObjectProperty<UserDTO> currentUser = new SimpleObjectProperty<>();

    private SessionManager() {}

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public void setSession(String jwt, UserDTO user) {
        this.token.set(jwt);
        this.currentUser.set(user);
    }

    public void clear() {
        token.set(null);
        currentUser.set(null);
    }

    public boolean isAuthenticated() {
        return token.get() != null && !token.get().isEmpty();
    }

    public String getAuthHeader() {
        return "Bearer " + token.get();
    }

    public StringProperty tokenProperty() {
        return token;
    }

    public ObjectProperty<UserDTO> currentUserProperty() {
        return currentUser;
    }

    public UserDTO getCurrentUser() {
        return currentUser.get();
    }
}