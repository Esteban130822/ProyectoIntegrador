
package app.ui;

import app.dao.PlanDAO;
import app.model.Plan;
import app.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PlanController {

    @FXML private TextField txtTitulo;
    @FXML private ComboBox<String> cmbNivel;
    @FXML private ComboBox<String> cmbGrado;
    @FXML private TextArea txtContenidos;
    @FXML private TextArea txtCompetencias;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private Label lblMsg;

    private final PlanDAO planDAO = new PlanDAO();
    private Usuario usuarioActual;
    private Plan planEditando;
    private Runnable onSaved;

    public void init(Usuario usuario, Plan plan, Runnable onSaved) {
        this.usuarioActual = usuario;
        this.planEditando = plan;
        this.onSaved = onSaved;

        cmbNivel.getItems().addAll("Primaria", "Secundaria", "Media");
        cmbGrado.getItems().addAll("1°","2°","3°","4°","5°","6°","7°","8°","9°","10°","11°");
        cmbEstado.getItems().addAll("Borrador", "En revisión", "Aprobado");
        cmbEstado.getSelectionModel().select("Borrador");

        if (plan != null) {
            txtTitulo.setText(plan.getTitulo());
            cmbNivel.setValue(plan.getNivel());
            cmbGrado.setValue(plan.getGrado());
            txtContenidos.setText(plan.getContenidos());
            txtCompetencias.setText(plan.getCompetencias());
            cmbEstado.setValue(plan.getEstado());
        } else {
            // Docente no puede crear en "Aprobado" directamente
            if ("docente".equals(usuarioActual.getRol())) {
                cmbEstado.getItems().remove("Aprobado");
            }
        }
    }

    @FXML
    private void onGuardar() {
        if (txtTitulo.getText().isBlank() || cmbNivel.getValue() == null || cmbGrado.getValue() == null ||
            txtContenidos.getText().isBlank() || txtCompetencias.getText().isBlank()) {
            lblMsg.setText("Complete todos los campos.");
            return;
        }
        if (planEditando == null) {
            Plan nuevo = new Plan(
                    txtTitulo.getText(),
                    cmbNivel.getValue(),
                    cmbGrado.getValue(),
                    txtContenidos.getText(),
                    txtCompetencias.getText(),
                    cmbEstado.getValue(),
                    usuarioActual.getId()
            );
            boolean ok = planDAO.crear(nuevo);
            lblMsg.setText(ok ? "Plan creado." : "No se pudo crear.");
            if (ok && onSaved != null) onSaved.run();
        } else {
            planEditando.setTitulo(txtTitulo.getText());
            planEditando.setNivel(cmbNivel.getValue());
            planEditando.setGrado(cmbGrado.getValue());
            planEditando.setContenidos(txtContenidos.getText());
            planEditando.setCompetencias(txtCompetencias.getText());
            planEditando.setEstado(cmbEstado.getValue());
            boolean ok = planDAO.actualizar(planEditando);
            lblMsg.setText(ok ? "Plan actualizado." : "No se pudo actualizar.");
            if (ok && onSaved != null) onSaved.run();
        }
    }
}
