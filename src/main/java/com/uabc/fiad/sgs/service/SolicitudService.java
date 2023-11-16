package com.uabc.fiad.sgs.service;

import java.time.LocalDateTime;
import java.util.*;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.uabc.fiad.sgs.entity.Solicitud;

@Service
public class SolicitudService implements ISolicitudService{
	
    @Autowired
    private JdbcTemplate template;

	/**
	 * Función para regresar todas las solicitudes de salida creadas por
	 * el usuario con la id indicada
	 * @param userId    la id del usuario
	 * @return          lista con las solicitudes de salida del usuario
	 */
	@Override
	public List<Solicitud> findByUserId(Integer userId) {
		return template.query(
				"""
                select *
                from solicitud s
                left join estado_solicitud es
                on s.idEstado_Solicitud = es.idEstado_Solicitud
                where s.idUsuario = ?;
                """,
				(rs, rowNum) ->
						new Solicitud(
								rs.getInt("idSolicitud"),
								rs.getString("Nombre_Evento"),
								rs.getObject("Fecha_Salida", LocalDateTime.class),
								rs.getObject("Fecha_Regreso", LocalDateTime.class),
								rs.getFloat("Costo"),
								rs.getString("Lugar"),
								rs.getString("Reporte_Final"),
								rs.getInt("idUsuario"),
								rs.getInt("idCarrera"),
								rs.getString("DescripcionEstado"),
								rs.getInt("idEstado_Solicitud")
						),
				userId
		);
	}

	/**
	 * Encuentra una solicitud mediante su id
	 * @param id    la id de la solicitud
	 * @return      la solicitud encontrada
	 */
	@Override
	public Optional<Solicitud> findById(Integer id) {
		List<Solicitud> solicitudes = template.query(
				"""
                select *
                from solicitud s
                left join estado_solicitud es
                on s.idEstado_Solicitud = es.idEstado_Solicitud
                where s.idSolicitud = ?;
                """,
				(rs, rowNum) ->
						new Solicitud(
								rs.getInt("idSolicitud"),
								rs.getString("Nombre_Evento"),
								rs.getObject("Fecha_Salida", LocalDateTime.class),
								rs.getObject("Fecha_Regreso", LocalDateTime.class),
								rs.getFloat("Costo"),
								rs.getString("Lugar"),
								rs.getString("Reporte_Final"),
								rs.getInt("idUsuario"),
								rs.getInt("idCarrera"),
								rs.getString("DescripcionEstado"),
								rs.getInt("idEstado_Solicitud")
						),
				id
		);

		if (solicitudes.size() == 0) {
			return Optional.empty();
		} else {
			return Optional.of(solicitudes.get(0));
		}
	}

	/**
	 * Lista los recursos existentes, junto con una bandera indicando si está
	 * registrado en la solicitud de salida y los detalles en caso de que sí.
	 * @param id    la id de la solicitud
	 * @return      una lista de mapas con la descripción de cada recurso (desc),
	 *              si está registrado (registrado) y los detalles (detalles)
	 */
	@Override
	public List<Map<String, Object>> listarRecursos(Integer id) {
		List<Map<String, Object>> res = template.queryForList(
				"""
                select r.Rec_Descripcion, sr.idSolicitudRecursos, sr.Detalles
                from recursos r
                left outer join (select * from solicitud_recursos sr where sr.idSolicitud = ?) sr
                on sr.idRecursos = r.idRecursos;
                """,
				id
		);

		List<Map<String, Object>> recursos = new ArrayList<>();
		for (Map<String, Object> r : res) {
			Map<String, Object> rec = new HashMap<>();
			rec.put("desc", r.get("Rec_Descripcion"));
			rec.put("registrado", r.get("idSolicitudRecursos") != null);
			rec.put("detalles", r.get("Detalles"));
			recursos.add(rec);
		}

		return recursos;
	}

	/**
	 * Lista las actividades asociadas, junto con una bandera indicando si está
	 * registrado en la solicitud de salida y la descripción en caso de que sí.
	 * @param id    la id de la solicitud
	 * @return      una lista de mapas con el nombre de cada actividad (nombre),
	 *              si está registrado (registrado) y la descripción (desc)
	 */
	@Override
	public List<Map<String, Object>> listarActividadesAsociadas(Integer id) {
		List<Map<String, Object>> res = template.queryForList(
				"""
                select aa.Act_Nombre, aas.Descripcion, aas.Act_asociada_idAct_Asociada
                from act_asociada aa\s
                left outer join (select * from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = ?) aas
                on aas.Act_asociada_idAct_Asociada = aa.idAct_Asociada;
                """,
				id
		);

		List<Map<String, Object>> recursos = new ArrayList<>();
		for (Map<String, Object> r : res) {
			Map<String, Object> rec = new HashMap<>();
			rec.put("nombre", r.get("Act_Nombre"));
			rec.put("desc", r.get("Descripcion"));
			rec.put("registrado", r.get("Act_asociada_idAct_Asociada") != null);
			recursos.add(rec);
		}

		return recursos;
	}

	/**
	 * Lista las firmas que necesita una solicitud, junto con el usuario que la firmó
	 * en caso de que esté firmada
	 *
	 * @param id la solicitud de la cual consultar sus firmas
	 * @return lista con mapas de las firmas, con llaves: rol, rol a firmar;
	 * usuario, id del usuario que firmó, o nulo si no hay firma;
	 */
	@Override
	public List<Map<String, Object>> listarFirmas(Integer id) {
		return template.queryForList(
				"""
                select r.Rol_Descripcion as rol, fs.idUsuario as usuario
                from firmas_solicitud fs
                join rol r\s
                on fs.idRol = r.idRol
                where fs.idSolicitud = ?;
                """,
				id
		);
	}

	/**
	 * Lista todas las solicitudes que requieren la firma del usuario con la id indicada.
	 * @param rolId		la id del rol a consultar sus firmas
	 * @return          lista solicitudes pendientes por firmar por el usuario
	 */
	@Override
	public List<Solicitud> listarSolicitudesPendientes(Integer rolId) {
		return template.query(
				"""
				select s.*, es.DescripcionEstado\s
				from solicitud s
				right join firmas_solicitud fs\s
				on s.idSolicitud = fs.idSolicitud
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				where fs.idRol = ? and fs.idUsuario is null;
				""",
				(rs, rowNum) ->
						new Solicitud(
								rs.getInt("idSolicitud"),
								rs.getString("Nombre_Evento"),
								rs.getObject("Fecha_Salida", LocalDateTime.class),
								rs.getObject("Fecha_Regreso", LocalDateTime.class),
								rs.getFloat("Costo"),
								rs.getString("Lugar"),
								rs.getString("Reporte_Final"),
								rs.getInt("idUsuario"),
								rs.getInt("idCarrera"),
								rs.getString("DescripcionEstado")
						),
				rolId
		);
	}

	/**
     * Guarda una solicitud de salida
     * @param solicitud  Solicitud a guardar
     * @return           id de la Solicitud registrada
     */
	@Override
	public Integer saveSolicitud(Solicitud solicitud) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template)
                .withProcedureName("insert_solicitud");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("nombre_evento", solicitud.getNombreEvento());
        inParamMap.put("fecha_salida", solicitud.getFechaSalida());
        inParamMap.put("fecha_regreso", solicitud.getFechaRegreso());
        inParamMap.put("costo", solicitud.getCosto());
        inParamMap.put("lugar", solicitud.getLugar());
        inParamMap.put("id_usuario", solicitud.getIdUsuario());
        inParamMap.put("id_carrera", solicitud.getIdCarrera());
        inParamMap.put("id_estado_solicitud", solicitud.getEstadoSolicitud());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> resMap = simpleJdbcCall.execute(in);

        Integer idSolicitud = (Integer) resMap.get("idsolicitud");
        System.out.println(resMap);
        return idSolicitud;
	}

	/**
	 * Guardo los recursos solicitados en la solicitud
	 * @param idSolicitud	id de la solicitud
	 * @param idRecurso		id de los recursos solicitidos
	 * @param detalle		detalle del recurso solo si es necesario especificarlo
	 */
	@Override
	public void saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle) {
		if(detalle == null) {
	        String sql = "INSERT INTO solicitud_recursos (idSolicitud, idRecursos) VALUES(?, ?);";
	        template.update(sql, idSolicitud, idRecurso);
		}else {
			String sql = "INSERT INTO solicitud_recursos (idSolicitud, idRecursos, Detalles) VALUES(?, ?, ?);";
	        template.update(sql, idSolicitud, idRecurso,detalle);
		}
        
		
	}

	/**
	 * Guarda las actividades relacionadas a la actividad
	 * @param idSolicitud	id de la solicitud
	 * @param idAtividad	id de las actividades a realizar
	 * @param detalle		detalle de la actividad solo si es necesario especificarlo (otro)
	 */
	@Override
	public void saveActividad(Integer idSolicitud, Integer idAtividad, String detalle) {
		if(detalle == null) {
			String sql = "INSERT INTO act_asociada_solicitud(Act_Asociada_idAct_Asociada, Solicitud_idSolicitud)"
					+ "VALUES(?, ?);";
	        template.update(sql, idAtividad ,idSolicitud);
		}else {
			String sql = "INSERT INTO act_asociada_solicitud(Act_Asociada_idAct_Asociada, Solicitud_idSolicitud, Descripcion)"
					+ "VALUES(?, ?, ?);";
	        template.update(sql, idAtividad ,idSolicitud,detalle);
		}
	}

	/**
	 * Registra las firmas requeridas en la solicitud, de acuerdo al usuario
	 * @param idSolicitud	id la solcitud
	 * @param firmas		lista de las firmas requeridas (id de los usuarios a firmar)
	 */
	@Override
	public void saveFirmas(Integer idSolicitud, List<Integer> firmas) {
		String sql = "INSERT INTO firmas_solicitud (idSolicitud, idRol) VALUES(?,?);";
		for (Integer firma : firmas) {
			template.update(sql,idSolicitud,firma);
		}
		
	}

	/**
	 * Cambia el estado de una solicitud a cancelado
	 * @param idSolicitud	id de la solcitud a cancelar
	 * @return				si se registró o no la solicitud
	 */
	@Override
	public Boolean cancelarSolicitud(Integer idSolicitud) {
        String sql = "UPDATE solicitud SET idEstado_Solicitud  = 8 WHERE idSolicitud = ?";

        int filasAfectadas = template.update(sql,idSolicitud);

        if (filasAfectadas > 0) {
            System.out.println("La actualización se realizó con éxito.");
            return true;
        } else {
            System.out.println("No se encontraron filas que cumplan con la condición.");
            return false;
        }
	}

}
