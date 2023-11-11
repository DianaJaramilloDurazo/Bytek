package com.uabc.fiad.sgs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Solicitud {

    private Integer idSolicitud;

    private String nombreEvento;

    private LocalDateTime fechaSalida;

    private LocalDateTime fechaRegreso;

    private float costo;

    private String lugar;

    private String reporteFinal;

    private Integer idUsuario;

    private Integer idCarrera;

    private String estadoSolicitud;
}
