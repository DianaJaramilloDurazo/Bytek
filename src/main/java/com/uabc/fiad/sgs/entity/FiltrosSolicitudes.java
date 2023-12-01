package com.uabc.fiad.sgs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltrosSolicitudes {
    private Integer idCategoria;
    private Integer idActAsociada;
    private Integer numEmpleado;
    private Integer idCarrera;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    private String nombreEvento;
    private String lugar;
    private Integer idEstado;
    private String nombreDocente;
    private Integer page;

}
