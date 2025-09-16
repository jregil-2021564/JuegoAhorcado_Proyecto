package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PalabrasDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Conexion cn = new Conexion(); // Instantiate the connection class

    /**
     * Obtiene una palabra aleatoria de la base de datos.
     * @return Objeto Palabras con la palabra, pista y categoría.
     * Retorna null si no se encuentra ninguna palabra.
     */
    public Palabras obtenerPalabraAleatoria() {
        String sql = "SELECT * FROM palabras ORDER BY RAND() LIMIT 1";
        Palabras palabra = null;
        try {
            con = cn.Conexion(); // This should be the correct method call
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                palabra = new Palabras();
                // Map the results from your database table columns
                palabra.setCodigoPalabra(rs.getInt("codigo_palabra"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista(rs.getString("pista"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener palabra aleatoria: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources in a separate helper method for cleaner code
            cerrarRecursos();
        }
        return palabra;
    }

    /**
     * Método auxiliar para cerrar los recursos de la base de datos.
     */
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}