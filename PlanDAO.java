
package app.dao;

import app.db.DatabaseConnection;
import app.model.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    public boolean crear(Plan p) {
        String sql = "INSERT INTO planes(titulo, nivel, grado, contenidos, competencias, estado, creado_por) " +
                     "VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getNivel());
            ps.setString(3, p.getGrado());
            ps.setString(4, p.getContenidos());
            ps.setString(5, p.getCompetencias());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getCreadoPor());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error crear plan: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Plan p) {
        String sql = "UPDATE planes SET titulo=?, nivel=?, grado=?, contenidos=?, competencias=?, estado=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getNivel());
            ps.setString(3, p.getGrado());
            ps.setString(4, p.getContenidos());
            ps.setString(5, p.getCompetencias());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar plan: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM planes WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar plan: " + e.getMessage());
            return false;
        }
    }

    public List<Plan> listarTodos() {
        String sql = "SELECT * FROM planes ORDER BY id DESC";
        List<Plan> out = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                out.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listar planes: " + e.getMessage());
        }
        return out;
    }

    public List<Plan> listarPorUsuario(int userId) {
        String sql = "SELECT * FROM planes WHERE creado_por=? ORDER BY id DESC";
        List<Plan> out = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) out.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error listar planes por usuario: " + e.getMessage());
        }
        return out;
    }

    private Plan mapRow(ResultSet rs) throws SQLException {
        return new Plan(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("nivel"),
                rs.getString("grado"),
                rs.getString("contenidos"),
                rs.getString("competencias"),
                rs.getString("estado"),
                rs.getInt("creado_por")
        );
    }
}
