package com.uabc.fiad.sgs.service;
import java.util.List;
import java.util.Map;
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

    /**
     * Lista los recursos existentes, junto con una bandera indicando si está
     * registrado en la solicitud de salida y los detalles en caso de que sí.
     * @param id    la id de la solicitud
     * @return      una lista de mapas con la descripción de cada recurso (desc),
     *              si está registrado (registrado) y los detalles (detalles)
     */
    List<Map<String, Object>> listarRecursos(Integer id);

    /**
     * Lista las actividades asociadas, junto con una bandera indicando si está
     * registrado en la solicitud de salida y la descripción en caso de que sí.
     * @param id    la id de la solicitud
     * @return      una lista de mapas con el nombre de cada actividad (nombre),
     *              si está registrado (registrado) y la descripción (desc)
     */
    List<Map<String, Object>> listarActividadesAsociadas(Integer id);

    /**
     * Lista las firmas que necesita una solicitud, junto con el usuario que la firmó
     * en caso de que esté firmada
     * @param id    la solicitud de la cual consultar sus firmas
     * @return      lista con mapas de las firmas, con llaves: rol, rol a firmar;
     *              usuario, id del usuario que firmó, o nulo si no hay firma;
     */
    List<Map<String, Object>> listarFirmas(Integer id);
	
	Integer saveSolicitud(Solicitud solicitud);
	
    void saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle);
    
    void saveActividad(Integer idSolicitud, Integer idAtividad, String detalle);
    
    void saveFirmas(Integer idSolicitud,List<Integer> firmas);
}
