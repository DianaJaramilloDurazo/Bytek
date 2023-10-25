package com.uabc.fiad.sgs.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;

    private String username;

    private String apPaterno;

    private String apMaterno;

    private String correo;

    private Integer numEmpleado;

    private String rol;

    private Integer idRol;

    public UsuarioDTO(Integer idUsuario, String username, String apPaterno, String apMaterno, String correo, Integer numEmpleado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.correo = correo;
        this.numEmpleado = numEmpleado;
    }

    public UsuarioDTO(Integer idUsuario, String username, String apPaterno, String apMaterno, String correo, String rol, Integer idRol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.correo = correo;
        this.rol = rol;
        this.idRol = idRol;
    }
}
