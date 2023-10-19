package com.uabc.fiad.sgs.Entity;

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

}
