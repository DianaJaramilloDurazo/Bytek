package com.uabc.fiad.sgs.service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;

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
     * Lista todas las solicitudes que requieren la firma del usuario con la id indicada.
     * @param rolId		la id del rol a consultar sus firmas
     * @return          lista solicitudes pendientes por firmar por el usuario
     */
    List<Solicitud> listarSolicitudesPendientes(Integer rolId);

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
    Boolean saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle);

    /**
     * Guarda las actividades relacionadas a la actividad
     * @param idSolicitud	id de la solicitud
     * @param idAtividad	id de las actividades a realizar
     * @param detalle		detalle de la actividad solo si es necesario especificarlo (otro)
     */
    Boolean saveActividad(Integer idSolicitud, Integer idAtividad, String detalle);

    /**
     * Registra las firmas requeridas en la solicitud, de acuerdo al usuario
     * @param idSolicitud	id la solcitud
     * @param firmas		lista de las firmas requeridas (id de los usuarios a firmar)
     */
    void saveFirmas(Integer idSolicitud,List<Integer> firmas);

    /**
     * Firma una solicitud con un usuario.
     * @param idSolicitud   la id de la solicitud a firmar
     * @param idUsuario     id del usuario a firmar
     * @param idRol         id del rol con el cual va a firmar
     */
    boolean firmarSolicitud(Integer idSolicitud, Integer idUsuario, Integer idRol);

    /**
     * Cambia el estado de una solicitud a cancelado
     * @param idSolicitud	id de la solcitud a cancelar
     * @return				si se registró o no la solicitud
     */
    Boolean cancelarSolicitud(Integer idSolicitud);

    /**
     * Actualiza la información de una solicitud
     * @param solicitud     solicitud a editar su información
     * @return              resultado sobre si se logró actualizar la solicitud
     */
    Boolean updateSolicitud(Solicitud solicitud);

    /**
     * Borra los recursos que se quitaron de una solicitud al editarla
     * @param idSolicitud   id de la solicitud a editar
     * @param recursos      lista con los id de los recursos a borrar
     */
    void deleteRecursos(Integer idSolicitud, Set<Integer> recursos);

    /**
     * Borra las actividades quitaron de una solicitud de salida
     * @param idSolicitud   id de la solicitud a editar
     * @param actividades      lista con los id de las actividades a borrar
     */
    void deleteActividades(Integer idSolicitud, Set<Integer> actividades);

    /**
     * Actuliza los detalles de los recursos que lo requieran
     * @param idSolicitud   id de la solicitud a editar
     * @param idRecurso     id del recurso a editar su detalle
     * @param detalle       detalle a asignar al recurso
     */
    void updateDetalleRecurso(Integer idSolicitud,Integer idRecurso, String detalle);

    /**
     * Actualiza los detalles de los recursos que lo requieran
     * @param idSolicitud   id de la solicitud a editar
     * @param idActividad   id de la actividad a actulizar su detalle (Otra: )
     * @param detalle       detalle a actualizar
     */
    void updateDetalleActividad(Integer idSolicitud,Integer idActividad, String detalle);

    /**
     * borra las todas las firmas de una solicitud de salida
     * @param idSolicitud   id de la solicitud a borrar sus firmas
     */
    void reiniciarFirmas(Integer idSolicitud);

    /**
     * Obtiene una lista de correos de los responsables a firmar una solicitud
     * @param idSolicitud   id de la solicitud
     * @return              lista con correos de los responsables a firmar la solicitud
     */
    List<String> obtnerCorrreosFirmas(Integer idSolicitud);

    /**
     * Cambia el estado de una solicitud a "En correción"
     * @param idSolicitud   id de la solicitud a cambiar su estado
     * @return               resultado sobre si se logró cambiar el esatdo de la solicitud
     */
    boolean rechzarSolicitud(Integer idSolicitud);

    public boolean guardarReferenciaReporteFinal(String idReporteDrive,Integer idSolicitud);
	
    
	/**
	 * Obtiene los datos de los encargados de firmar una determinada solcitud
	 * @param idSolicitud   id de la solicitud a cambiar su estado
	 * @return              Lista con datos de los enecatgados a firmar (Nombre del rol y correo)
	 */
    List<Map<String, Object>> DatosRolesFirma(Integer idSolicitud);

    /**
     * Regresa una lista de solicitudes con una cantidad especifica para realizar una paginación
     * @param limit     cantidad de registros a regresar
     * @param offset    Indica el punto de inicio de los registros que se recuperarán
     * @param filtros   los filtros a aplicar
     * @param rolId   	id de rol que firmará la solicitudes
     * @return          lista de solicitudes pendientes por firmar
     */
    List<Solicitud> PaginacionSolicitudesPendientes(Integer limit, Integer offset, Filtros filtros, Integer rolId);

    /**
     * Recupera el total de solicitudes pendientes por firmar de acuerdo a un rol (sirve para la paginación)
     * @param id	id del rol que firmará las solicitudes
     * @return		total de solicitudes pendientesn por firmar
     */
    Integer totalSolicitudesPendientes(Integer id);

    public List<Solicitud> listarSolicitudesRealizadas(Integer rolId);
	public List<Solicitud> listarSolicitudesRealizadasById(Integer userId);
}
