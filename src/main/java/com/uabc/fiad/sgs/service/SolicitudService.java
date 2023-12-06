package com.uabc.fiad.sgs.service;

import com.uabc.fiad.sgs.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SolicitudService implements ISolicitudService {

	@Autowired
	private JdbcTemplate template;

	/**
	 * Función para regresar todas las solicitudes de salida creadas por el usuario
	 * con la id indicada
	 * 
	 * @param userId la id del usuario
	 * @return lista con las solicitudes de salida del usuario
	 */
	@Override
	public List<Solicitud> findByUserId(Integer userId) {
		// Actualizacion del estado de las solicitudes que esten en estado Firmado
		// cambiar a estado En Curso
		String sqlUpdate1 = "update solicitud set idEstado_Solicitud = 4 where idUsuario = ? and idEstado_Solicitud = 3 and NOW() > Fecha_Salida;";
		// update,no es necesario saber si afecto alguna fila
		template.update(sqlUpdate1, userId);

		// Actualizacion del estado de las solicitudes del usuario cada vez que se
		// consultan
		// set 5 al estado porque significa que es el estado de Reporte_Pendiente
		// where idUsuario es ? porque se va leer el parametro de la funcion userId
		// where idEstado_Solicitud = 4 porque solamente cuando se esta en curso puede
		// pasar a pedir reporte automaticamente
		// where Now > fecha_regreso porque significa porque el tiempo actual real ya
		// paso al tiempo de la fecha de regreso
		String sqlUpdate2 = "update solicitud set idEstado_Solicitud = 5 where idUsuario = ? and idEstado_Solicitud = 4 and NOW() > Fecha_Regreso;";
		// update,no es necesario saber si afecto alguna fila
		template.update(sqlUpdate2, userId);

		return template.query("""
				select *
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				where s.idUsuario = ?;
				""", (rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
				rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
				rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
				rs.getInt("idUsuario"),
				rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")), userId);
	}

	/**
	 * Encuentra una solicitud mediante su id
	 * 
	 * @param id la id de la solicitud
	 * @return la solicitud encontrada
	 */
	@Override
	public Optional<Solicitud> findById(Integer id) {
		List<Solicitud> solicitudes = template.query("""
				select *
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				where s.idSolicitud = ?;
				""", (rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
				rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
				rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
				rs.getInt("idUsuario"),
				rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")), id);

		if (solicitudes.size() == 0) {
			return Optional.empty();
		} else {
			return Optional.of(solicitudes.get(0));
		}
	}

	/**
	 * Lista los recursos existentes, junto con una bandera indicando si está
	 * registrado en la solicitud de salida y los detalles en caso de que sí.
	 * 
	 * @param id la id de la solicitud
	 * @return una lista de mapas con la descripción de cada recurso (desc), si está
	 *         registrado (registrado) y los detalles (detalles)
	 */
	@Override
	public List<Map<String, Object>> listarRecursos(Integer id) {
		List<Map<String, Object>> res = template.queryForList("""
				select r.Rec_Descripcion, sr.idSolicitudRecursos, sr.Detalles,r.idRecursos
				from recursos r
				left outer join (select * from solicitud_recursos sr where sr.idSolicitud = ?) sr
				on sr.idRecursos = r.idRecursos;
				""", id);

		List<Map<String, Object>> recursos = new ArrayList<>();
		for (Map<String, Object> r : res) {
			Map<String, Object> rec = new HashMap<>();
			rec.put("desc", r.get("Rec_Descripcion"));
			rec.put("registrado", r.get("idSolicitudRecursos") != null);
			rec.put("detalles", r.get("Detalles"));
			rec.put("idRecurso", r.get("idRecursos"));
			recursos.add(rec);
		}

		return recursos;
	}

	/**
	 * Lista las actividades asociadas, junto con una bandera indicando si está
	 * registrado en la solicitud de salida y la descripción en caso de que sí.
	 * 
	 * @param id la id de la solicitud
	 * @return una lista de mapas con el nombre de cada actividad (nombre), si está
	 *         registrado (registrado) y la descripción (desc)
	 */
	@Override
	public List<Map<String, Object>> listarActividadesAsociadas(Integer id) {
		List<Map<String, Object>> res = template.queryForList("""
				select aa.Act_Nombre, aas.Descripcion, aas.Act_asociada_idAct_Asociada, aa.idAct_Asociada
				from act_asociada aa\s
				left outer join (select * from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = ?) aas
				on aas.Act_asociada_idAct_Asociada = aa.idAct_Asociada;
				""", id);

		List<Map<String, Object>> recursos = new ArrayList<>();
		for (Map<String, Object> r : res) {
			Map<String, Object> rec = new HashMap<>();
			rec.put("nombre", r.get("Act_Nombre"));
			rec.put("desc", r.get("Descripcion"));
			rec.put("registrado", r.get("Act_asociada_idAct_Asociada") != null);
			rec.put("idActividad", r.get("idAct_Asociada"));
			recursos.add(rec);
		}

		return recursos;
	}

	/**
	 * Lista las firmas que necesita una solicitud, junto con el usuario que la
	 * firmó en caso de que esté firmada
	 *
	 * @param id la solicitud de la cual consultar sus firmas
	 * @return lista con mapas de las firmas, con llaves: rol, rol a firmar;
	 *         usuario, id del usuario que firmó, o nulo si no hay firma;
	 */
	@Override
	public List<Map<String, Object>> listarFirmas(Integer id) {
		return template.queryForList("""
				select r.Rol_Descripcion as rol, fs.idUsuario as usuario
				from firmas_solicitud fs
				join rol r\s
				on fs.idRol = r.idRol
				where fs.idSolicitud = ?;
				""", id);
	}

	/**
	 * Lista todas las solicitudes que requieren la firma del usuario con la id
	 * indicada.
	 * 
	 * @param rolId la id del rol a consultar sus firmas
	 * @return lista solicitudes pendientes por firmar por el usuario
	 */
	@Override
	public List<Solicitud> listarSolicitudesPendientes(Integer rolId) {
		return template.query("""
				select s.*, es.DescripcionEstado
				from solicitud s
				right join firmas_solicitud fs
				on s.idSolicitud = fs.idSolicitud
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				where fs.idRol = ? and fs.idUsuario is null
				            and (s.idEstado_Solicitud = 1 or s.idEstado_Solicitud = 2);
				""", (rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
				rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
				rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
				rs.getInt("idUsuario"),
				rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")), rolId);
	}

	/**
	 * Guarda una solicitud de salida
	 * 
	 * @param solicitud Solicitud a guardar
	 * @return id de la Solicitud registrada
	 */
	@Override
	public Integer saveSolicitud(Solicitud solicitud) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template).withProcedureName("insert_solicitud");
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
	 * 
	 * @param idSolicitud id de la solicitud
	 * @param idRecurso   id de los recursos solicitidos
	 * @param detalle     detalle del recurso solo si es necesario especificarlo
	 * @return resultado sobre si se registro el recurso solicitado
	 */
	@Override
	public Boolean saveRecurso(Integer idSolicitud, Integer idRecurso, String detalle) {
		int filasAfectadas;
		if (detalle == null) {
			String sql = "INSERT INTO solicitud_recursos (idSolicitud, idRecursos) VALUES(?, ?);";
			filasAfectadas = template.update(sql, idSolicitud, idRecurso);
		} else {
			String sql = "INSERT INTO solicitud_recursos (idSolicitud, idRecursos, Detalles) VALUES(?, ?, ?);";
			filasAfectadas = template.update(sql, idSolicitud, idRecurso, detalle);
		}
		return filasAfectadas > 0;
	}

	/**
	 * Guarda las actividades relacionadas a la actividad
	 * 
	 * @param idSolicitud id de la solicitud
	 * @param idAtividad  id de las actividades a realizar
	 * @param detalle     detalle de la actividad solo si es necesario especificarlo
	 *                    (otro)
	 */
	@Override
	public Boolean saveActividad(Integer idSolicitud, Integer idAtividad, String detalle) {
		int filasAfectadas;
		if (detalle == null) {
			String sql = "INSERT INTO act_asociada_solicitud(Act_Asociada_idAct_Asociada, Solicitud_idSolicitud)"
					+ "VALUES(?, ?);";
			filasAfectadas = template.update(sql, idAtividad, idSolicitud);
		} else {
			String sql = "INSERT INTO act_asociada_solicitud(Act_Asociada_idAct_Asociada, Solicitud_idSolicitud, Descripcion)"
					+ "VALUES(?, ?, ?);";
			filasAfectadas = template.update(sql, idAtividad, idSolicitud, detalle);
		}
		return filasAfectadas > 0;
	}

	/**
	 * Registra las firmas requeridas en la solicitud, de acuerdo al usuario
	 * 
	 * @param idSolicitud id la solcitud
	 * @param firmas      lista de las firmas requeridas (id de los usuarios a
	 *                    firmar)
	 */
	@Override
	public void saveFirmas(Integer idSolicitud, List<Integer> firmas) {
		String sql = "INSERT INTO firmas_solicitud (idSolicitud, idRol) VALUES(?,?);";
		for (Integer firma : firmas) {
			template.update(sql, idSolicitud, firma);
		}

	}

	/**
	 * Firma una solicitud con un usuario.
	 *
	 * @param idSolicitud la id de la solicitud a firmar
	 * @param idUsuario   id del usuario a firmar
	 * @param idRol       id del rol con el cual va a firmar
	 */
	@Override
	public boolean firmarSolicitud(Integer idSolicitud, Integer idUsuario, Integer idRol) {
//
//		String sql = "update firmas_solicitud set idUsuario = ? where idRol = ? and idSolicitud = ?;";
//
//		int filasAfectadas = template.update(sql, idUsuario, idRol, idSolicitud);
//
//		return filasAfectadas > 0;

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template).withProcedureName("firmar_solicitud");
		Map<String, Object> inParamMap = new HashMap<>();
		inParamMap.put("id_solicitud", idSolicitud);
		inParamMap.put("id_usuario", idUsuario);
		inParamMap.put("id_rol", idRol);

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> resMap = simpleJdbcCall.execute(in);

		Object resObj = resMap.get("res");
		if (resObj == null) {
			return false;
		} else if (!(resObj instanceof Integer)) {
			return false;
		}

		return (Integer) resObj == 1;
	}

	/**
	 * Cambia el estado de una solicitud a cancelado
	 * 
	 * @param idSolicitud id de la solcitud a cancelar
	 * @return si se registró o no la solicitud
	 */
	@Override
	public Boolean cancelarSolicitud(Integer idSolicitud) {
		String sql = "UPDATE solicitud SET idEstado_Solicitud  = 8 WHERE idSolicitud = ?";

		int filasAfectadas = template.update(sql, idSolicitud);

		if (filasAfectadas > 0) {
			System.out.println("La actualización se realizó con éxito.");
			return true;
		} else {
			System.out.println("No se encontraron filas que cumplan con la condición.");
			return false;
		}
	}

	/**
	 * Actualiza la información de una solicitud
	 * 
	 * @param solicitud solicitud a editar su información
	 * @return resultado sobre si se logró actualizar la solicitud
	 */
	@Override
	public Boolean updateSolicitud(Solicitud solicitud) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template).withProcedureName("update_solicitud");
		Map<String, Object> inParamMap = new HashMap<>();
		inParamMap.put("id_solicitud", solicitud.getIdSolicitud());
		inParamMap.put("nombre_evento", solicitud.getNombreEvento());
		inParamMap.put("fecha_salida", solicitud.getFechaSalida());
		inParamMap.put("fecha_regreso", solicitud.getFechaRegreso());
		inParamMap.put("costo", solicitud.getCosto());
		inParamMap.put("lugar", solicitud.getLugar());
		inParamMap.put("id_carrera", solicitud.getIdCarrera());

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> resMap = simpleJdbcCall.execute(in);

		Object resObj = resMap.get("resultado");
		if (resObj == null) {
			return false;
		} else if (!(resObj instanceof Integer)) {
			return false;
		}

		return (Integer) resObj == 1;
	}

	/**
	 * Borra los recursos que se quitaron de una solicitud al editarla
	 * 
	 * @param idSolicitud id de la solicitud a editar
	 * @param recursos    lista con los id de los recursos a borrar
	 */
	@Override
	public void deleteRecursos(Integer idSolicitud, Set<Integer> recursos) {
		String sql = "DELETE FROM solicitud_recursos WHERE idSolicitud = ? AND idRecursos = ?";

		for (Integer recurso : recursos) {
			template.update(sql, idSolicitud, recurso);
		}

	}

	/**
	 * Borra las actividades quitaron de una solicitud de salida
	 * 
	 * @param idSolicitud id de la solicitud a editar
	 * @param actividades lista con los id de las actividades a borrar
	 */
	@Override
	public void deleteActividades(Integer idSolicitud, Set<Integer> actividades) {
		String sql = "DELETE FROM act_asociada_solicitud WHERE Solicitud_idSolicitud = ? AND Act_Asociada_idAct_Asociada = ?";

		for (Integer actividad : actividades) {
			template.update(sql, idSolicitud, actividad);
		}

	}

	/**
	 * Actuliza los detalles de los recursos que lo requieran
	 * 
	 * @param idSolicitud id de la solicitud a editar
	 * @param idRecurso   id del recurso a editar su detalle
	 * @param detalle     detalle a asignar al recurso
	 */
	@Override
	public void updateDetalleRecurso(Integer idSolicitud, Integer idRecurso, String detalle) {
		String sql = "UPDATE solicitud_recursos SET Detalles=? WHERE idSolicitud=? AND idRecursos=?;";
		template.update(sql, detalle, idSolicitud, idRecurso);

	}

	/**
	 * Actualiza los detalles de los recursos que lo requieran
	 * 
	 * @param idSolicitud id de la solicitud a editar
	 * @param idActividad id de la actividad a actulizar su detalle (Otra: )
	 * @param detalle     detalle a actualizar
	 */
	@Override
	public void updateDetalleActividad(Integer idSolicitud, Integer idActividad, String detalle) {
		String sql = "UPDATE sgs_db.act_asociada_solicitud SET Descripcion=? WHERE Act_Asociada_idAct_Asociada=? AND Solicitud_idSolicitud=?;";
		template.update(sql, detalle, idActividad, idSolicitud);

	}

	/**
	 * borra las todas las firmas de una solicitud de salida
	 * 
	 * @param idSolicitud id de la solicitud a borrar sus firmas
	 */
	@Override
	public void reiniciarFirmas(Integer idSolicitud) {
		// TODO Auto-generated method stub
		String sql = "UPDATE firmas_solicitud SET idUsuario= NULL WHERE idSolicitud  = ?;";
		template.update(sql, idSolicitud);
	}

	/**
	 * Obtiene una lista de correos de los responsables a firmar una solicitud
	 * 
	 * @param idSolicitud id de la solicitud
	 * @return lista con correos de los responsables a firmar la solicitud
	 */
	@Override
	public List<String> obtnerCorrreosFirmas(Integer idSolicitud) {
		String sql = "select fs.idRol from firmas_solicitud fs where idSolicitud = ?;";
		List<Integer> firmas = template.queryForList(sql, new Object[] { idSolicitud }, Integer.class);
		String correo;
		List<String> correos = new ArrayList<String>();
		for (Integer id : firmas) {
			String sql2 = "select r.Correo_rol from rol r where idRol=?;";
			correo = template.queryForObject(sql2, new Object[] { id }, String.class);
			correos.add(correo);
			System.out.println(correo);
		}

		System.out.println("HOLAAAAA");
		System.out.println(firmas);
		return correos;
	}

	/**
	 * Cambia el estado de una solicitud a "En correción"
	 * 
	 * @param idSolicitud id de la solicitud a cambiar su estado
	 * @return resultado sobre si se logró cambiar el esatdo de la solicitud
	 */
	@Override
	public boolean rechzarSolicitud(Integer idSolicitud) {
		String sql = "UPDATE solicitud SET idEstado_solicitud = 7 WHERE idSolicitud = ?;";
		int rowsAffected = template.update(sql, idSolicitud);
		return rowsAffected > 0;
	}

	/**
	 * Obtiene los datos de los encargados de firmar una determinada solcitud
	 * 
	 * @param idSolicitud id de la solicitud a cambiar su estado
	 * @return Lista con datos de los enecatgados a firmar (Nombre del rol y correo)
	 */
	@Override
	public List<Map<String, Object>> DatosRolesFirma(Integer idSolicitud) {
		String sql = "select r.Rol_Descripcion, r.Correo_rol from firmas_solicitud fs  LEFT join rol r on fs.idRol = r.idRol  where idSolicitud  = ?;";
		return template.queryForList(sql, idSolicitud);
	}

	@Override
	public boolean guardarReferenciaReporteFinal(String idReporteDrive, Integer idSolicitud) {
		String sql = "UPDATE solicitud SET Reporte_Final = ?, idEstado_Solicitud = 6 WHERE idSolicitud = ?;";
		int rowsAffected = template.update(sql, idReporteDrive, idSolicitud);
		return rowsAffected > 0;

	}
	@Override
	public boolean guardarReferenciaOficioSellado(String idReporteDrive, Integer idSolicitud) {
		String sql = "UPDATE solicitud SET Oficio_Sellado = ? WHERE idSolicitud = ?;";
		int rowsAffected = template.update(sql, idReporteDrive, idSolicitud);
		return rowsAffected > 0;

	}

	/**
	 * Regresa una lista de solicitudes con una cantidad especifica para realizar una paginación
	 * @param limit     cantidad de registros a regresar
	 * @param offset    Indica el punto de inicio de los registros que se recuperarán
	 * @param filtros   los filtros a aplicar
	 * @param rolId   	id de rol que firmará la solicitudes
	 * @return          lista de solicitudes pendientes por firmar
	 */
	@Override
	public List<Solicitud> PaginacionSolicitudesPendientes(Integer limit, Integer offset, Filtros filtros,
			Integer rolId) {
		return template.query("""
					SELECT s.*, es.DescripcionEstado
					FROM solicitud s
					RIGHT JOIN firmas_solicitud fs ON s.idSolicitud = fs.idSolicitud
					LEFT JOIN estado_solicitud es ON s.idEstado_Solicitud = es.idEstado_Solicitud
					WHERE fs.idRol = ? AND fs.idUsuario IS NULL
					AND (s.idEstado_Solicitud = 1 OR s.idEstado_Solicitud = 2)
					LIMIT ? OFFSET ?;
				""", (rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
				rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
				rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
				rs.getInt("idUsuario"),
				rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")), rolId,
				limit, offset);
	}

	/**
	 * Recupera el total de solicitudes pendientes por firmar de acuerdo a un rol (sirve para la paginación)
	 * @param id	id del rol que firmará las solicitudes
	 * @return		total de solicitudes pendientes por firmar
	 */
	@Override
	public Integer totalSolicitudesPendientes(Integer id) {
		String sql = """
					select COUNT(*)
					from solicitud s
					right join firmas_solicitud fs
					on s.idSolicitud = fs.idSolicitud
					left join estado_solicitud es
					on s.idEstado_Solicitud = es.idEstado_Solicitud
					where fs.idRol = ? and fs.idUsuario is null
					and (s.idEstado_Solicitud = 1 or s.idEstado_Solicitud = 2);
				""";
		return template.queryForObject(sql, new Object[] { id }, Integer.class);
	}

	/**
	 * Lista todas las solicitudes que esten en Pendientes, Finalizada o Cancelada
	 * 
	 * @param rolId la id del rol a consultar sus firmas
	 * @return lista solicitudes finalizadas 	 
	 * */
	@Override
	public List<Solicitud> listarSolicitudesRealizadas(Integer rolId) {
		return template.query("""
				select s.*, es.DescripcionEstado
				from solicitud s
				right join firmas_solicitud
				on s.idSolicitud = firmas_solicitud.idSolicitud
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				where firmas_solicitud.idRol = ? and firmas_solicitud.idUsuario is null
				            and (s.idEstado_Solicitud = 5 or s.idEstado_Solicitud = 6 or s.idEstado_Solicitud = 8);
				""", (rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
				rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
				rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
				rs.getInt("idUsuario"),
				rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")), rolId);	
	}


	@Override
	public List<Solicitud> listarSolicitudesRealizadasById(Integer userId) {
		// Actualizacion del estado de las solicitudes que esten en estado Firmado
		// cambiar a estado En Curso
		String sqlUpdate1 = "update solicitud set idEstado_Solicitud = 4 where idUsuario = ? and idEstado_Solicitud = 3 and NOW() > Fecha_Salida;";
		// update,no es necesario saber si afecto alguna fila
		template.update(sqlUpdate1, userId);

		// Actualizacion del estado de las solicitudes del usuario cada vez que se
		// consultan
		// set 5 al estado porque significa que es el estado de Reporte_Pendiente
		// where idUsuario es ? porque se va leer el parametro de la funcion userId
		// where idEstado_Solicitud = 4 porque solamente cuando se esta en curso puede
		// pasar a pedir reporte automaticamente
		// where Now > fecha_regreso porque significa porque el tiempo actual real ya
		// paso al tiempo de la fecha de regreso
		String sqlUpdate2 = "update solicitud set idEstado_Solicitud = 5 where idUsuario = ? and idEstado_Solicitud = 4 and NOW() > Fecha_Regreso;";
		// update,no es necesario saber si afecto alguna fila
		template.update(sqlUpdate2, userId);

		return template.query("""
				select *
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				where s.idUsuario = ? and 
				(s.idEstado_Solicitud = 5 or s.idEstado_Solicitud = 6 or s.idEstado_Solicitud = 8);;
				""", (rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
				rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
				rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
				rs.getInt("idUsuario"),
				rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")), userId);
	}

	public Integer totalSolicitudesRealizadas(Integer rolId, FiltrosSolicitudes filtros) {

		return template.queryForObject("""
				select count(*)
				from solicitud s
				right join firmas_solicitud
				on s.idSolicitud = firmas_solicitud.idSolicitud
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				left join usuario u
				on s.idUsuario = u.idUsuario 
				where firmas_solicitud.idRol = ?
				and (s.idEstado_Solicitud = 5 or s.idEstado_Solicitud = 6 or s.idEstado_Solicitud = 8)
				and s.Nombre_Evento like ?
				and (u.Categoria_idCategoria1 = ? or ? is null)
				and (? in (select aas.Act_Asociada_idAct_Asociada from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = s.idSolicitud) or ? is null)
				and (Num_Empleado = ? or ? is null)
				and (s.idCarrera = ? or ? is null)
				and s.Lugar like ?
				and (s.idEstado_Solicitud = ? or ? is null)
				and u.Usr_Nombre like ?
				and (s.Fecha_Regreso > ? or ? is null)
				and (s.Fecha_Salida < ? or ? is null);
				""",
				Integer.class,
				rolId,
				"%" + filtros.getNombreEvento() + "%",
				filtros.getIdCategoria(),
				filtros.getIdCategoria(),
				filtros.getIdActAsociada(),
				filtros.getIdActAsociada(),
				filtros.getNumEmpleado(),
				filtros.getNumEmpleado(),
				filtros.getIdCarrera(),
				filtros.getIdCarrera(),
				"%" + filtros.getLugar() + "%",
				filtros.getIdEstado(),
				filtros.getIdEstado(),
				"%" + filtros.getNombreDocente() + "%",
				filtros.getFechaInicio(),
				filtros.getFechaInicio(),
				filtros.getFechaFin(),
				filtros.getFechaFin());
	}

	public List<Solicitud> paginarSolicitudesRealizadas(Integer rolId, FiltrosSolicitudes filtros, Integer limit, Integer offset) {
		return template.query("""
				select s.*, es.DescripcionEstado, u.Categoria_idCategoria1
				from solicitud s
				right join firmas_solicitud
				on s.idSolicitud = firmas_solicitud.idSolicitud
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				left join usuario u
				on s.idUsuario = u.idUsuario 
				where firmas_solicitud.idRol = ?
				and (s.idEstado_Solicitud = 5 or s.idEstado_Solicitud = 6 or s.idEstado_Solicitud = 8)
				and s.Nombre_Evento like ?
				and (u.Categoria_idCategoria1 = ? or ? is null)
				and (? in (select aas.Act_Asociada_idAct_Asociada from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = s.idSolicitud) or ? is null)
				and (Num_Empleado = ? or ? is null)
				and (s.idCarrera = ? or ? is null)
				and s.Lugar like ?
				and (s.idEstado_Solicitud = ? or ? is null)
				and u.Usr_Nombre like ?
				and (s.Fecha_Regreso > ? or ? is null)
				and (s.Fecha_Salida < ? or ? is null)
				limit ? offset ?;
				""",
				(rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
						rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
						rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
						rs.getInt("idUsuario"),
						rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")),
				rolId,
				"%" + filtros.getNombreEvento() + "%",
				filtros.getIdCategoria(),
				filtros.getIdCategoria(),
				filtros.getIdActAsociada(),
				filtros.getIdActAsociada(),
				filtros.getNumEmpleado(),
				filtros.getNumEmpleado(),
				filtros.getIdCarrera(),
				filtros.getIdCarrera(),
				"%" + filtros.getLugar() + "%",
				filtros.getIdEstado(),
				filtros.getIdEstado(),
				"%" + filtros.getNombreDocente() + "%",
				filtros.getFechaInicio(),
				filtros.getFechaInicio(),
				filtros.getFechaFin(),
				filtros.getFechaFin(),
				limit,
				offset);
	}

	public Integer totalSolicitudesRealizadasById(Integer userId, FiltrosSolicitudes filtros) {
		if (filtros.getNombreDocente() == null) {
			filtros.setNombreDocente("");
		}

		return template.queryForObject("""
				select count(*)
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				left join usuario u
				on u.idUsuario = s.idUsuario
				where s.idUsuario = ? and 
				(s.idEstado_Solicitud = 5 or s.idEstado_Solicitud = 6 or s.idEstado_Solicitud = 8)
				and s.Nombre_Evento like ?
				and (u.Categoria_idCategoria1 = ? or ? is null)
				and (? in (select aas.Act_Asociada_idAct_Asociada from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = s.idSolicitud) or ? is null)
				and (Num_Empleado = ? or ? is null)
				and (s.idCarrera = ? or ? is null)
				and s.Lugar like ?
				and (s.idEstado_Solicitud = ? or ? is null)
				and u.Usr_Nombre like ?
				and (s.Fecha_Regreso > ? or ? is null)
				and (s.Fecha_Salida < ? or ? is null)
				""",
				Integer.class,
				userId,
				"%" + filtros.getNombreEvento() + "%",
				filtros.getIdCategoria(),
				filtros.getIdCategoria(),
				filtros.getIdActAsociada(),
				filtros.getIdActAsociada(),
				filtros.getNumEmpleado(),
				filtros.getNumEmpleado(),
				filtros.getIdCarrera(),
				filtros.getIdCarrera(),
				"%" + filtros.getLugar() + "%",
				filtros.getIdEstado(),
				filtros.getIdEstado(),
				"%" + filtros.getNombreDocente() + "%",
				filtros.getFechaInicio(),
				filtros.getFechaInicio(),
				filtros.getFechaFin(),
				filtros.getFechaFin()
				);
	}
	public List<Solicitud> paginarSolicitudesRealizadasById(Integer userId, FiltrosSolicitudes filtros, Integer limit, Integer offset) {
		if (filtros.getNombreDocente() == null) {
			filtros.setNombreDocente("");
		}

		return template.query("""
				select *
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				left join usuario u
				on u.idUsuario = s.idUsuario
				where s.idUsuario = ? and 
				(s.idEstado_Solicitud = 5 or s.idEstado_Solicitud = 6 or s.idEstado_Solicitud = 8)
				and s.Nombre_Evento like ?
				and (u.Categoria_idCategoria1 = ? or ? is null)
				and (? in (select aas.Act_Asociada_idAct_Asociada from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = s.idSolicitud) or ? is null)
				and (Num_Empleado = ? or ? is null)
				and (s.idCarrera = ? or ? is null)
				and s.Lugar like ?
				and (s.idEstado_Solicitud = ? or ? is null)
				and u.Usr_Nombre like ?
				and (s.Fecha_Regreso > ? or ? is null)
				and (s.Fecha_Salida < ? or ? is null)
				limit ? offset ?;
				""",
				(rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
						rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
						rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"), 
						rs.getInt("idUsuario"),
						rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")),
				userId,
				"%" + filtros.getNombreEvento() + "%",
				filtros.getIdCategoria(),
				filtros.getIdCategoria(),
				filtros.getIdActAsociada(),
				filtros.getIdActAsociada(),
				filtros.getNumEmpleado(),
				filtros.getNumEmpleado(),
				filtros.getIdCarrera(),
				filtros.getIdCarrera(),
				"%" + filtros.getLugar() + "%",
				filtros.getIdEstado(),
				filtros.getIdEstado(),
				"%" + filtros.getNombreDocente() + "%",
				filtros.getFechaInicio(),
				filtros.getFechaInicio(),
				filtros.getFechaFin(),
				filtros.getFechaFin(),
				limit,
				offset);
	}

	public List<String> listarEstados() {
		return template.query(
    		"""
				select DescripcionEstado from estado_solicitud es;
				""",
				(rs, rowNum) -> rs.getString("DescripcionEstado")
		);
	}

	public Integer totalSolicitudesRealizadasSecretario(FiltrosSolicitudes filtros) {
		// Actualizacion del estado de las solicitudes que esten en estado Firmado
		// cambiar a estado En Curso
		String sqlUpdate1 = "update solicitud set idEstado_Solicitud = 4 where idEstado_Solicitud = 3 and NOW() > Fecha_Salida;";
		// update,no es necesario saber si afecto alguna fila
		template.update(sqlUpdate1);

		// Actualizacion del estado de las solicitudes del usuario cada vez que se
		// consultan
		// set 5 al estado porque significa que es el estado de Reporte_Pendiente
		// where idUsuario es ? porque se va leer el parametro de la funcion userId
		// where idEstado_Solicitud = 4 porque solamente cuando se esta en curso puede
		// pasar a pedir reporte automaticamente
		// where Now > fecha_regreso porque significa porque el tiempo actual real ya
		// paso al tiempo de la fecha de regreso
		String sqlUpdate2 = "update solicitud set idEstado_Solicitud = 5 where idEstado_Solicitud = 4 and NOW() > Fecha_Regreso;";
		// update,no es necesario saber si afecto alguna fila
		template.update(sqlUpdate2);

		return template.queryForObject("""
				select count(*)
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				left join usuario u
				on s.idUsuario = u.idUsuario
				where s.Nombre_Evento like ?
				and (u.Categoria_idCategoria1 = ? or ? is null)
				and (? in (select aas.Act_Asociada_idAct_Asociada from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = s.idSolicitud) or ? is null)
				and (Num_Empleado = ? or ? is null)
				and (s.idCarrera = ? or ? is null)
				and s.Lugar like ?
				and (s.idEstado_Solicitud = ? or ? is null)
				and u.Usr_Nombre like ?
				and (s.Fecha_Regreso > ? or ? is null)
				and (s.Fecha_Salida < ? or ? is null);
				""",
				Integer.class,
				
				"%" + filtros.getNombreEvento() + "%",
				filtros.getIdCategoria(),
				filtros.getIdCategoria(),
				filtros.getIdActAsociada(),
				filtros.getIdActAsociada(),
				filtros.getNumEmpleado(),
				filtros.getNumEmpleado(),
				filtros.getIdCarrera(),
				filtros.getIdCarrera(),
				"%" + filtros.getLugar() + "%",
				filtros.getIdEstado(),
				filtros.getIdEstado(),
				"%" + filtros.getNombreDocente() + "%",
				filtros.getFechaInicio(),
				filtros.getFechaInicio(),
				filtros.getFechaFin(),
				filtros.getFechaFin());
	}


	public List<Solicitud> paginarSolicitudesRealizadasSecretario(FiltrosSolicitudes filtros, Integer limit, Integer offset) {
		return template.query("""
				select s.*, es.DescripcionEstado, u.Categoria_idCategoria1
				from solicitud s
				left join estado_solicitud es
				on s.idEstado_Solicitud = es.idEstado_Solicitud
				left join usuario u
				on s.idUsuario = u.idUsuario 
				where s.Nombre_Evento like ?
				and (u.Categoria_idCategoria1 = ? or ? is null)
				and (? in (select aas.Act_Asociada_idAct_Asociada from act_asociada_solicitud aas where aas.Solicitud_idSolicitud = s.idSolicitud) or ? is null)
				and (Num_Empleado = ? or ? is null)
				and (s.idCarrera = ? or ? is null)
				and s.Lugar like ?
				and (s.idEstado_Solicitud = ? or ? is null)
				and u.Usr_Nombre like ?
				and (s.Fecha_Regreso > ? or ? is null)
				and (s.Fecha_Salida < ? or ? is null)
				order by field(es.DescripcionEstado, 'Firma parcial', 'Firmada', 'En curso', 'Reporte pendiente', 'Finalizada') desc
				limit ? offset ?;
				""",
				(rs, rowNum) -> new Solicitud(rs.getInt("idSolicitud"), rs.getString("Nombre_Evento"),
						rs.getObject("Fecha_Salida", LocalDateTime.class), rs.getObject("Fecha_Regreso", LocalDateTime.class),
						rs.getFloat("Costo"), rs.getString("Lugar"), rs.getString("Reporte_Final"), rs.getString("Oficio_Sellado"),
						rs.getInt("idUsuario"),
						rs.getInt("idCarrera"), rs.getString("DescripcionEstado"), rs.getInt("idEstado_Solicitud")),
				
				"%" + filtros.getNombreEvento() + "%",
				filtros.getIdCategoria(),
				filtros.getIdCategoria(),
				filtros.getIdActAsociada(),
				filtros.getIdActAsociada(),
				filtros.getNumEmpleado(),
				filtros.getNumEmpleado(),
				filtros.getIdCarrera(),
				filtros.getIdCarrera(),
				"%" + filtros.getLugar() + "%",
				filtros.getIdEstado(),
				filtros.getIdEstado(),
				"%" + filtros.getNombreDocente() + "%",
				filtros.getFechaInicio(),
				filtros.getFechaInicio(),
				filtros.getFechaFin(),
				filtros.getFechaFin(),
				limit,
				offset);
	}

	@Override
	public boolean guardarReporteFinal(byte[] reporteFinal, Integer idSolicitud) {
		String sql = "UPDATE solicitud SET Reporte_Final=? WHERE idSolicitud=?;";
		int rowsAffected = template.update(sql, reporteFinal,idSolicitud);
		if(rowsAffected > 0) {
			template.update("UPDATE solicitud SET idEstado_Solicitud=6 WHERE idSolicitud=?;",idSolicitud);
		}
		return rowsAffected > 0;

	}
	@Override
	public boolean guardarOficio(byte[] oficio, Integer idSolicitud) {
		String sql = "UPDATE solicitud SET Oficio_Sellado=? WHERE idSolicitud=?;";
		int rowsAffected = template.update(sql, oficio,idSolicitud);
		return rowsAffected > 0;
	}

	@Override
	public byte[] obtenerReporteFinal(Integer idSolicitud) {
		// TODO Auto-generated method stub 
		String sql = "SELECT s.Reporte_Final from solicitud s WHERE s.idSolicitud =?;";
		byte[] reporte = template.queryForObject(sql, new Object[]{idSolicitud}, byte[].class);
		return reporte;
	}
	
	@Override
	public byte[] obtenerOficioSellado(Integer idSolicitud) {
		// TODO Auto-generated method stub 
		String sql = "SELECT s.Oficio_Sellado from solicitud s WHERE s.idSolicitud =?;";
		byte[] reporte = template.queryForObject(sql, new Object[]{idSolicitud}, byte[].class);
		return reporte;
	}


}
