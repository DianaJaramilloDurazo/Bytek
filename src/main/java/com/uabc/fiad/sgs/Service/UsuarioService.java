package com.uabc.fiad.sgs.Service;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private JdbcTemplate template;
    @Override
    public Optional<UsuarioDTO> findById(Integer id) {
        return template.queryForObject(
                "SELECT Usr_Nombre, Ap_Paterno, Correo,Num_Empleado FROM usuario WHERE idUsuario=?;",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new UsuarioDTO(
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Correo"),
                                rs.getString("Num_Empleado")
                        ))
        );
    }

    /**
     * Hace una búsqueda por correo de un usuario.
     *
     * @param correo    el correo del usuario a buscar
     * @return          el usuario encontrado
     */
    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return template.queryForObject(
                "SELECT Usr_Nombre, Ap_Paterno, Correo,Num_Empleado FROM usuario WHERE Correo=?;",
                new Object[]{correo},
                (rs, rowNum) ->
                        Optional.of(new Usuario(
                                null,
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getString("Usr_Pwd"),
                                rs.getInt("Num_Empleado"),
                                rs.getInt("Carrera_idCarrera"),
                                rs.getInt("Categoria_idCategoria"),
                                rs.getInt("Estado_idEstado")
                        ))
        );
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return template.query(
                "SELECT Usr_Nombre, Ap_Paterno, Correo,Num_Empleado FROM usuario;",
                (rs, rowNum) ->
                        new UsuarioDTO(
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Correo"),
                                rs.getString("Num_Empleado")

                        )
        );
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param usuario   el usuario a guardar
     * @return          si se logró guardar el usuario
     */
    @Override
    public Boolean save(Usuario usuario) {
        //call insert_usuario("Dalinar", "Blackthorn", "Kholin", "blackthorn@uabc.edu.mx", "BLACKTHORN", "ADFCG", 23521, 1, 1, @res);
        //
        //select @res;

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template)
                .withProcedureName("insert_usuario");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("nombre", usuario.getUsername());
        inParamMap.put("ap_paterno", usuario.getApPaterno());
        inParamMap.put("ap_materno", usuario.getApMaterno());
        inParamMap.put("correo", usuario.getCorreo());
        inParamMap.put("pwd", usuario.getPassword());
        inParamMap.put("num_empleado", usuario.getNumEmpleado());
        inParamMap.put("id_carrera", usuario.getIdCarrera());
        inParamMap.put("id_categoria", usuario.getIdCategoria());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> resMap = simpleJdbcCall.execute(in);

        Object resObj = resMap.get("res");
        if (resObj == null) {
            return false;
        } else if (!(resObj instanceof Integer)) {
            return false;
        }

        return (Integer)resObj == 1;
    }
}
