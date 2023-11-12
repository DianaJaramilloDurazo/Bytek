package com.uabc.fiad.sgs.service;

import java.util.HashMap;
import java.util.Map;

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

}
