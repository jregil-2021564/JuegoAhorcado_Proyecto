package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Clase DAO para manejar operaciones de base de datos relacionadas con la entidad Usuarios.
 * Proporciona métodos para validar credenciales de inicio de sesión, registrar nuevos usuarios
 * y verificar la existencia de un nombre de usuario.
 */
public class UsuariosDAO {
    private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * Valida las credenciales de un usuario utilizando el procedimiento almacenado sp_VerificarLogin.
     *
     * @param username Nombre de usuario proporcionado
     * @param password Contraseña proporcionada
     * @return Objeto Usuarios si las credenciales son válidas, null en caso contrario
     */
    public Usuarios validar(String username, String password) {
        Usuarios usuario = null;
        String sql = "CALL sp_verificar_login(?, ?)";
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setCodigoUsuario(rs.getInt("codigo_Usuario"));
                usuario.setUsername(rs.getString("username"));
            }
        } catch (Exception e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return usuario;
    }

    /**
     * Registra un nuevo usuario en la base de datos utilizando el procedimiento almacenado sp_AgregarUsuario.
     *
     * @param username Nombre de usuario a registrar
     * @param password Contraseña del usuario
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrar(String username, String password) {
        String sql = "CALL sp_agregar_usuario(?, ?)";
        boolean registrado = false;
        
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            int filas = ps.executeUpdate();
            registrado = filas > 0;
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return registrado;
    }

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos utilizando el procedimiento almacenado sp_VerificarUsuarioExistente.
     *
     * @param username Nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     */
    public boolean verificarUsuarioExistente(String username) {
        String sql = "CALL sp_verificar_usuario_existente(?)";
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
            System.err.println("Error al verificar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return existe;
    }
}