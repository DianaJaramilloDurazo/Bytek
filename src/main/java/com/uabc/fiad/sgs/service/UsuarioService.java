package com.uabc.fiad.sgs.service;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Usuario;
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
public class UsuarioService implements IUsuarioService {

    @Autowired
    private JdbcTemplate template;

    /**
     * Búsqueda de usuario por id
     * @param id    id del usuario a buscar
     * @return      el usuario que se encontró
     */
    @Override
    public Optional<Usuario> findById(Integer id) {
        return template.queryForObject(
                "SELECT u.idUsuario, u.Usr_Nombre ,u.Ap_Paterno, u.Ap_Materno,u.Num_Empleado, u.Correo, " +
                        "c.idCarrera AS 'carrera', ca.idCategoria as 'categoria',u.Estado_idEstado AS estado " +
                        "FROM usuario u " +
                        "LEFT JOIN rol ON u.idUsuario = rol.Usuario_idUsuario " +
                        "LEFT JOIN carrera c ON u.Carrera_idCarrera = c.idCarrera " +
                        "LEFT JOIN categoria ca ON u.Categoria_idCategoria1 = ca.idCategoria " +
                        "WHERE idUsuario = ?;",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Usuario(
                                rs.getInt("idUsuario"),
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getInt("Num_Empleado"),
                                rs.getInt("carrera"),
                                rs.getInt("categoria"),
                                rs.getInt("estado"),0
                        ))
        );
    }

    @Override
    public Optional<Usuario> findRolById(Integer id) {
        return template.queryForObject(
                "SELECT u.idUsuario,u.Usr_Nombre, u.Ap_Paterno, u.Ap_Materno, r.Correo_rol AS Correo, u.Num_Empleado, u.Carrera_idCarrera AS carrera, u.Categoria_idCategoria1 AS categoria, u.Estado_idEstado AS estado, r.idRol FROM rol r INNER JOIN usuario u ON r.Usuario_idUsuario = u.idUsuario WHERE r.idRol=?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Usuario(
                                rs.getInt("idUsuario"),
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getInt("Num_Empleado"),
                                rs.getInt("carrera"),
                                rs.getInt("categoria"),
                                rs.getInt("estado"),
                                rs.getInt("idRol")
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

        // Hacer una query para obtener todos los usuarios con ese correo, que debería ser máximo 1, y los guardamos en
        // una lista
        List<Usuario> usuarios = template.query(
                "call get_usuario(?)",
                (rs, rowNum) ->
                        new Usuario(
                                rs.getInt("idUsuario"),
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getString("Usr_Pwd"),
                                rs.getInt("Num_Empleado"),
                                rs.getInt("Carrera_idCarrera"),
                                rs.getInt("Categoria_idCategoria1"),
                                rs.getInt("Estado_idEstado"),
                                rs.getInt("idRol")
                        ),
                correo
        );

        // Si la lista está vacía regresar un optional vacío, si no regresamos el primer elemento
        if (usuarios.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(usuarios.get(0));
        }
    }

    /**
     * Encuentra un usuario en base a su número de empleado.
     *
     * @param numEmpleado   el número de empleado a buscar
     * @return              el usuario encontrado
     */
    @Override
    public Optional<Usuario> findByNumEmpleado(Integer numEmpleado) {

        // Hacer una query para obtener todos los usuarios con ese número de empleado, que debería ser máximo 1,
        // y los guardamos en una lista
        List<Usuario> usuarios = template.query(
                "SELECT * FROM usuario u WHERE u.Num_Empleado = ?;",
                new Object[]{numEmpleado},
                (rs, rowNum) ->
                        new Usuario(
                                null,
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getString("Usr_Pwd"),
                                rs.getInt("Num_Empleado"),
                                rs.getInt("Carrera_idCarrera"),
                                rs.getInt("Categoria_idCategoria1"),
                                rs.getInt("Estado_idEstado"),0
                        )
        );

        // Si la lista está vacía regresar un optional vacío, si no regresamos el primer elemento
        if (usuarios.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(usuarios.get(0));
        }
    }

    /**
     * Obtiene una lista de todos los usuarios
     * @return      lista con todos los usuarios registrados
     */
    @Override
    public List<UsuarioDTO> findAll() {
        return template.query(
                "SELECT Usr_Nombre, Ap_Paterno,Ap_Materno, Correo,Num_Empleado FROM usuario;",
                (rs, rowNum) ->
                        new UsuarioDTO(
                                rs.getInt("idUsuario"),
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getInt("Num_Empleado")

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

    /**
     * Actuliza la informacion del usuario
     * @param usuario   usuario al que se le actualizará su información
     * @return          si se logró actualizar la información
     */
    @Override
    public Boolean update(Usuario usuario) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template)
                .withProcedureName("update_usuario");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("id_usuario", usuario.getIdUsuario());
        inParamMap.put("nombre", usuario.getUsername());
        inParamMap.put("ap_paterno", usuario.getApPaterno());
        inParamMap.put("ap_materno", usuario.getApMaterno());
        inParamMap.put("p_carrera", usuario.getIdCarrera());
        inParamMap.put("p_categoria", usuario.getIdCategoria());
        inParamMap.put("p_estado", usuario.getIdEstado());

        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> resMap = simpleJdbcCall.execute(in);

        Object resObj = resMap.get("resultado");
        if (resObj == null) {
            return false;
        } else if (!(resObj instanceof Integer)) {
            return false;
        }

        return (Integer)resObj == 1;
    }

    /**
     *  Actualiza el rol de un usuario
     * @param idUser    id del usuario a editar su rol
     * @param idRol     nuevo rol a asignar al usuario
     * @return          si se logró actualizar el rol
     */
    @Override
    public Boolean updateRol(Integer idUser, Integer idRol) {
        String sql = "UPDATE rol SET Usuario_idUsuario = ? WHERE idRol = ?";

        int filasAfectadas = template.update(sql, idUser, idRol);

        if (filasAfectadas > 0) {
            System.out.println("La actualización se realizó con éxito.");
            return true;
        } else {
            System.out.println("No se encontraron filas que cumplan con la condición.");
            return false;
        }
    }

    /**
     * Regresa una lista de usuarios con una cantidad especifica para realizar una paginación
     * @param limit     cantidad de registros a regresar
     * @param offset    Indica el punto de inicio de los registros que se recuperarán
     * @param filtros   los filtros a aplicar
     * @return          lista de usuarios
     */
    @Override
    public List<UsuarioDTO> pagination(Integer limit, Integer offset, Filtros filtros) {
        String nombre = "%" + filtros.getNombre() + "%";
        return template.query(
                """
                (
                    (SELECT u.idUsuario,u.Usr_Nombre ,u.Ap_Paterno ,u.Ap_Materno ,r.Correo_rol as Correo,r.Rol_Descripcion as rol, r.idRol
                    FROM rol AS r
                    INNER JOIN usuario u
                    ON r.Usuario_idUsuario = u.idUsuario
                	where concat_ws(' ', u.Usr_Nombre, u.Ap_Paterno, u.Ap_Materno) like ?)
                UNION all
                	(SELECT u.idUsuario, u.Usr_Nombre, u.Ap_Paterno,u.Ap_Materno, u.Correo,'Docente' as rol, 0 as idRol
                	FROM usuario u
                	where concat_ws(' ', Usr_Nombre, Ap_Paterno, Ap_Materno) like ?)
                ) limit ? offset ?;
                """,
                (rs, rowNum) ->
                        new UsuarioDTO(
                                rs.getInt("idUsuario"),
                                rs.getString("Usr_Nombre"),
                                rs.getString("Ap_Paterno"),
                                rs.getString("Ap_Materno"),
                                rs.getString("Correo"),
                                rs.getString("rol"),
                                rs.getInt("idRol")

                        ),
                nombre, nombre, limit, offset
                );
    }

    /**
     * Recupera el total de usuarios que estan registrados (sirve para la paginación)
     *
     * @param filtros   los filtros a aplicar
     * @return          total de registros en la tabla usuarios
     */
    @Override
    public Integer TotalRecords(Filtros filtros) {

        return template.queryForObject("""
                select count(*) from usuario u
                where concat_ws(" ", u.Usr_Nombre, u.Ap_Paterno, u.Ap_Materno) like ?
                """,
                Integer.class, "%" + filtros.getNombre() + "%")
                + template.queryForObject("SELECT COUNT(*) FROM rol", Integer.class)
                ;
    }

    /**
     * Obtiene una lista de carreras con su id y nombre
     * @return  lista de carreras
     */
    @Override
    public List<Map<String, Object>> listarCarreras() {
        return template.queryForList("SELECT *FROM Carrera");
    }

    /**
     * Obtiene una lista de categorías con su id y nombre
     * @return  lista de categorías
     */

    @Override
    public List<Map<String, Object>> listarCategorias() {
        return template.queryForList("SELECT *FROM Categoria");
    }

    /**
     * Obtiene una lista de los estados que puede tener el usuario (activo, inactivo) con su id y nombre
     * @return lista de estados
     */
    @Override
    public List<Map<String, Object>> listarEstado() {
        return template.queryForList("SELECT *FROM Estado");
    }

    /**
     * Obtiene una lista de roles que peuden tener los usuarios
     * @return  lista de roles
     */
    @Override
    public List<Map<String, Object>> listarRoles() {
        return template.queryForList("SELECT r.idRol ,r.Rol_Descripcion as rol from rol r");
    }

    /**
     * Obtiene el rol del un usuario
     * @param id    id del usuario a buscar su rol
     * @return      id del rol del usuario
     */
    @Override
    public Integer findIdRolById(Integer id) {
        List<Integer> results = template.query("SELECT r.idRol FROM rol r WHERE r.Usuario_idUsuario = ?", new Object[]{id}, (rs, rowNum) -> rs.getInt("idRol"));

        if (results.isEmpty()) {
            return 0; // O si no se encuentra un resultado.
        }

        return results.get(0); // Devuelve el primer resultado
    }

    /**
     * Guarda  el registro de un rol
     * @param rol   Rol a registrar
     * @return      Si se logró registrar el rol
     */
    @Override
    public Boolean saveRol(Rol rol) {


        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template)
                .withProcedureName("insert_rol");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("rol", rol.getRol());
        inParamMap.put("idUsuario", rol.getIdUsuario());
        inParamMap.put("correo", rol.getCorreo());
        inParamMap.put("pwd", rol.getPassword());
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

    /**
     * Busca un rol por medio de su correo
     * @param correo    Correo del rol a buscar
     * @return          Retorna el rol en caso de encontrarlo
     */
    @Override
    public Optional<Rol> findRolByCorreo(String correo) {
        List<Rol> roles = template.query(
                "SELECT * FROM rol WHERE Correo_Rol=?;",
                new Object[]{correo},
                (rs, rowNum) ->
                        new Rol(
                                rs.getInt("idRol"),
                                rs.getString("Rol_Descripcion"),
                                rs.getString("Correo_rol"),
                                rs.getString("password"),
                                rs.getInt("Usuario_idUsuario")
                        )

        );

        // Si la lista está vacía regresar un optional vacío, si no regresamos el primer elemento
        if (roles.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(roles.get(0));
        }
    }


}
