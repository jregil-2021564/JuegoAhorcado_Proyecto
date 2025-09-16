package com.joabregil.Ahorcado.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_Usuario")
    private Integer codigo_Usuario;

    @Column(name = "username", length = 100, unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "fecha_Regsitro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha_Regsitro;

    public Integer getCodigo_Usuario() {
        return codigo_Usuario;
    }

    public void setCodigo_Usuario(Integer codigo_Usuario) {
        this.codigo_Usuario = codigo_Usuario;
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

    public LocalDateTime getFecha_Regsitro() {
        return fecha_Regsitro;
    }

    public void setFecha_Regsitro(LocalDateTime fecha_Regsitro) {
        this.fecha_Regsitro = fecha_Regsitro;
    }
}
