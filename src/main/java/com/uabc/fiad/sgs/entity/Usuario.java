package com.uabc.fiad.sgs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {

    private Integer idUsuario;

    private String username;

    private String apPaterno;

    private String apMaterno;

    private String correo;

    private String password;

    private Integer numEmpleado;

    private Integer idCarrera;

    private Integer idCategoria;

    private Integer idEstado;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String username, String apPaterno, String apMaterno, String correo, Integer numEmpleado, Integer idCarrera, Integer idCategoria, Integer idEstado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.correo = correo;
        this.numEmpleado = numEmpleado;
        this.idCarrera = idCarrera;
        this.idCategoria = idCategoria;
        this.idEstado = idEstado;
    }
}
