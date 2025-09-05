package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PalabrasDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Palabras obtenerPalabraAleatoria() {
        Palabras palabra = null;
        String sql = "CALL sp_ObtenerPalabraAleatoria()";
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                palabra = new Palabras();
                palabra.setCodigoPalabra(rs.getInt("codigoPalabra"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista(rs.getString("pista"));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener palabra aleatoria: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return palabra;
    }

    public List<Palabras> listarPalabras() {
        List<Palabras> lista = new ArrayList<>();
        String sql = "CALL sp_ListarPalabras()";
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Palabras palabra = new Palabras();
                palabra.setCodigoPalabra(rs.getInt("codigoPalabra"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista(rs.getString("pista"));
                lista.add(palabra);
            }
        } catch (Exception e) {
            System.out.println("Error al listar palabras: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public boolean agregarPalabra(String palabra, String pista) {
        String sql = "CALL sp_AgregarPalabra(?, ?)";
        boolean agregado = false;
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, palabra);
            ps.setString(2, pista);
            int filas = ps.executeUpdate();
            agregado = filas > 0;
        } catch (Exception e) {
            System.out.println("Error al agregar palabra: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return agregado;
    }
}