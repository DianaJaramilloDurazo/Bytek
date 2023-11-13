package com.uabc.fiad.sgs.service;
import java.util.List;

import com.uabc.fiad.sgs.entity.Solicitud;

public interface ISolicitudService {
	
	
	Integer saveSolicitud(Solicitud solicitud );
	
    void saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle);
    
    void saveActividad(Integer idSolicitud, Integer idAtividad, String detalle);
    
    void saveFirmas(Integer idSolicitud,List<Integer> firmas);
}
