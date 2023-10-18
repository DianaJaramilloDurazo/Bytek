package com.uabc.fiad.sgs.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;

    private String apPaterno;

    private String apMaterno;

    private String correo;

    private String numEmpleado;

    public UsuarioDTO(String username, String apPaterno, String correo,String numEmpleado) {
        this.username = username;
        this.apPaterno = apPaterno;
        this.correo = correo;
        this.numEmpleado = numEmpleado;
    }
}
