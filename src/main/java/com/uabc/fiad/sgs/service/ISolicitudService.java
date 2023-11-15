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

    /**
     * Guarda una solicitud de salida
     * @param solicitud  Solicitud a guardar
     * @return           id de la Solicitud registrada
     */
	Integer saveSolicitud(Solicitud solicitud);

    /**
     * Guardo los recursos solicitados en la solicitud
     * @param idSolicitud	id de la solicitud
     * @param idRecurso		id de los recursos solicitidos
     * @param detalle		detalle del recurso solo si es necesario especificarlo
     */
    void saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle);

    /**
     * Guarda las actividades relacionadas a la actividad
     * @param idSolicitud	id de la solicitud
     * @param idAtividad	id de las actividades a realizar
     * @param detalle		detalle de la actividad solo si es necesario especificarlo (otro)
     */
    void saveActividad(Integer idSolicitud, Integer idAtividad, String detalle);

    /**
     * Registra las firmas requeridas en la solicitud, de acuerdo al usuario
     * @param idSolicitud	id la solcitud
     * @param firmas		lista de las firmas requeridas (id de los usuarios a firmar)
     */
    void saveFirmas(Integer idSolicitud,List<Integer> firmas);

    /**
     * Cambia el estado de una solicitud a cancelado
     * @param idSolicitud	id de la solcitud a cancelar
     * @return				si se registró o no la solicitud
     */
    Boolean cancelarSolicitud(Integer idSolicitud);
}
