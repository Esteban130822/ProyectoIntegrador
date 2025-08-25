
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

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void onLogin(ActionEvent e) {
        String correo = txtCorreo.getText();
        String pass = txtPassword.getText();

        Usuario u = usuarioDAO.login(correo, pass);
        if (u == null) {
            lblError.setText("Credenciales inválidas.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.init(u);

            Stage stage = (Stage) ((Control) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Panel - " + u.getRol());
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblError.setText("No se pudo abrir el panel.");
        }
    }

    @FXML
    private void onGoToRegister(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            Stage stage = (Stage) ((Control) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 520, 500));
            stage.setTitle("Registro");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblError.setText("No se pudo abrir registro.");
        }
    }
}
