
package app.ui;

import app.dao.PlanDAO;
import app.dao.UsuarioDAO;
import app.model.Plan;
import app.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DashboardController {

    @FXML private Label lblBienvenida;
    @FXML private Tab tabPlanes;
    @FXML private Tab tabUsuarios;

    // Contenido del tab de planes
    @FXML private TableView<Plan> tblPlanes;
    @FXML private TableColumn<Plan, String> colTitulo;
    @FXML private TableColumn<Plan, String> colNivel;
    @FXML private TableColumn<Plan, String> colGrado;
    @FXML private TableColumn<Plan, String> colEstado;

    @FXML private TextField txtBuscar;
    @FXML private Button btnNuevoPlan;
    @FXML private Button btnEditarPlan;
    @FXML private Button btnEliminarPlan;
    @FXML private Button btnAprobarPlan;

    private final PlanDAO planDAO = new PlanDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Usuario usuarioActual;

    private ObservableList<Plan> planes = FXCollections.observableArrayList();

    public void init(Usuario usuario) {
        this.usuarioActual = usuario;
        lblBienvenida.setText("Bienvenido, " + usuario.getNombre() + " (" + usuario.getRol() + ")");

        // Permisos por rol
        switch (usuario.getRol()) {
            case "administrador":
                tabUsuarios.setDisable(false);
                btnAprobarPlan.setDisable(true); // solo coordinador aprueba
                break;
            case "coordinador":
                tabUsuarios.setDisable(true);
                btnAprobarPlan.setDisable(false);
                break;
            case "docente":
            default:
                tabUsuarios.setDisable(true);
                btnAprobarPlan.setDisable(true);
                break;
        }

        // Config columnas (usar toString del Plan; simplificamos con PropertyValueFactory via getters)
        colTitulo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitulo()));
        colNivel.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNivel()));
        colGrado.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGrado()));
        colEstado.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEstado()));

        recargarTabla();
    }

    private void recargarTabla() {
        planes.clear();
        if ("docente".equals(usuarioActual.getRol()))
            planes.addAll(planDAO.listarPorUsuario(usuarioActual.getId()));
        else
            planes.addAll(planDAO.listarTodos());
        tblPlanes.setItems(planes);
    }

    @FXML
    private void onNuevoPlan() {
        abrirPlanForm(null);
    }

    @FXML
    private void onEditarPlan() {
        Plan seleccionado = tblPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        abrirPlanForm(seleccionado);
    }

    @FXML
    private void onEliminarPlan() {
        Plan seleccionado = tblPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        if (planDAO.eliminar(seleccionado.getId())) recargarTabla();
    }

    @FXML
    private void onAprobarPlan() {
        Plan seleccionado = tblPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        seleccionado.setEstado("Aprobado");
        planDAO.actualizar(seleccionado);
        recargarTabla();
    }

    private void abrirPlanForm(Plan plan) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/plan.fxml"));
            Parent root = loader.load();
            PlanController controller = loader.getController();
            controller.init(usuarioActual, plan, () -> recargarTabla());
            Stage stage = new Stage();
            stage.setTitle(plan == null ? "Nuevo Plan" : "Editar Plan");
            stage.setScene(new Scene(root, 700, 520));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
