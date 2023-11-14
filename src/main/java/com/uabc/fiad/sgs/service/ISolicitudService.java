package com.uabc.fiad.sgs.service;
import java.util.List;
import java.util.Optional;

import com.uabc.fiad.sgs.entity.Solicitud;

public interface ISolicitudService {

    /**
     * Función para regresar todas las solicitudes de salida creadas por
     * el usuario con la id indicada
     * @param userId    la id del usuario
     * @return          lista con las solicitudes de salida del usuario
     */
    List<Solicitud> findByUserId(Integer userId);

    /**
     * Encuentra una solicitud mediante su id
     * @param id    la id de la solicitud
     * @return      la solicitud encontrada
     */
    Optional<Solicitud> findById(Integer id);
	
	Integer saveSolicitud(Solicitud solicitud );
	
    void saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle);
    
    void saveActividad(Integer idSolicitud, Integer idAtividad, String detalle);
    
    void saveFirmas(Integer idSolicitud,List<Integer> firmas);
}
