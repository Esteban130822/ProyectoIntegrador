
package app.db;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:database.db";

    static {
        // Inicializa tablas al cargar la clase
        try (Connection conn = getConnection()) {
            if (conn != null) {
                crearTablas(conn);
            }
        } catch (SQLException e) {
            System.err.println("Error inicializando BD: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void crearTablas(Connection conn) throws SQLException {
        String usuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "correo TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "rol TEXT NOT NULL" +
                ");";

        String planes = "CREATE TABLE IF NOT EXISTS planes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT NOT NULL," +
                "nivel TEXT NOT NULL," +           // Primaria, Secundaria, Media
                "grado TEXT NOT NULL," +           // 1°, 2°, etc.
                "contenidos TEXT NOT NULL," +
                "competencias TEXT NOT NULL," +
                "estado TEXT NOT NULL DEFAULT 'Borrador'," + // Borrador, En revisión, Aprobado
                "creado_por INTEGER NOT NULL," +
                "FOREIGN KEY(creado_por) REFERENCES usuarios(id)" +
                ");";

        try (Statement st = conn.createStatement()) {
            st.execute(usuarios);
            st.execute(planes);
        }
    }
}
