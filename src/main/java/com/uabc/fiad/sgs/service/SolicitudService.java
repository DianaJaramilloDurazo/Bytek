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
								rs.getString("DescripcionEstado")
						),
				userId
		);
	}

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
								rs.getString("DescripcionEstado")
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
     * Guarda una solicitud de salida
     * @param solicitud  Solicitud a guardar
     * @return           id de la Solicitud a guardar
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

	@Override
	public void saveFirmas(Integer idSolicitud, List<Integer> firmas) {
		String sql = "INSERT INTO firmas_solicitud (idSolicitud, idRol) VALUES(?,?);";
		for (Integer firma : firmas) {
			template.update(sql,idSolicitud,firma);
		}
		
	}

}
