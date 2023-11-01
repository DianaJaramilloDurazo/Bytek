package com.uabc.fiad.sgs.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rol {
    private Integer idRol;

    private String rol;

    private String correo;

    private String password;

    private Integer idUsuario;

}
