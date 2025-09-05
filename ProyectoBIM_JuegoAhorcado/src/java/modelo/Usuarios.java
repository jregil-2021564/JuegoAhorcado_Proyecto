package modelo;

import java.sql.Timestamp;

public class Usuarios {
    private int codigoUsuario;
    private String username;
    private String password;
    private Timestamp fechaRegistro;

    public Usuarios() {
    }

    public Usuarios(int codigoUsuario, String username, String password, Timestamp fechaRegistro) {
        this.codigoUsuario = codigoUsuario;
        this.username = username;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
