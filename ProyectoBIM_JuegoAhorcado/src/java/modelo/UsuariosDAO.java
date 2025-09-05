package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class UsuariosDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Usuarios validar(String username, String password) {
        Usuarios usuario = null;
        String sql = "CALL sp_VerificarLogin(?, ?)";
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setCodigoUsuario(rs.getInt("codigoUsuario"));
                usuario.setUsername(rs.getString("username"));
            }
        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
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
        return usuario;
    }

    public boolean registrar(String username, String password) {
        String sql = "CALL sp_AgregarUsuario(?, ?)";
        boolean registrado = false;
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            int filas = ps.executeUpdate();
            registrado = filas > 0;
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registrado;
    }

    public boolean verificarUsuarioExistente(String username) {
        String sql = "CALL sp_VerificarUsuarioExistente(?)";
        boolean existe = false;
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                existe = rs.getInt("existe") > 0;
            }
        } catch (Exception e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
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
        return existe;
    }
}