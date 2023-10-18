package com.uabc.fiad.sgs.Entity;

import lombok.Data;

@Data
public class Usuario {

    private Integer idUsuario;

    private String username;

    private String apPaterno;

    private String correo;

    private byte[] password;

    private byte[] salt;

    private Integer numEmpleado;

    private String carrera;

    private String categoria;

    private String estado;

}
