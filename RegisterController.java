
package app.ui;

import app.dao.UsuarioDAO;
import app.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;
    @FXML private Label lblInfo;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        cmbRol.getItems().addAll("docente", "coordinador", "administrador");
        cmbRol.getSelectionModel().select("docente");
    }

    @FXML
    private void onRegister(ActionEvent e) {
        if (txtNombre.getText().isBlank() || txtCorreo.getText().isBlank() ||
            txtPassword.getText().isBlank() || cmbRol.getValue() == null) {
            lblInfo.setText("Complete todos los campos.");
            return;
        }
        Usuario u = new Usuario(txtNombre.getText(), txtCorreo.getText(), txtPassword.getText(), cmbRol.getValue());
        boolean ok = usuarioDAO.registrar(u);
        if (ok) lblInfo.setText("Usuario registrado. Vuelva al login.");
        else lblInfo.setText("No se pudo registrar (¿correo duplicado?).");
    }

    @FXML
    private void onBackToLogin(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) ((Control) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 520, 420));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblInfo.setText("No se pudo volver al login.");
        }
    }
}
