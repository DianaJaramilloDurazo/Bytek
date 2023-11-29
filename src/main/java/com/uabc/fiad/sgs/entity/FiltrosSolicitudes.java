package com.uabc.fiad.sgs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltrosSolicitudes {
    public String nombreEvento;
    public String nombreDocente;
    public Integer page;

}
