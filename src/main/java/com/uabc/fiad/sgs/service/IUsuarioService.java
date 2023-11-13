package com.uabc.fiad.sgs.service;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);

    Optional<Usuario> findRolById(Integer id);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNumEmpleado(Integer numEmpleado);

    List<UsuarioDTO> findAll();

    Boolean save(Usuario usuario);

    Boolean update(Usuario usuario);

    Boolean updateRol(Integer idUser, Integer idRol);

    Integer TotalRecords(Filtros filtros);

    List<UsuarioDTO> pagination(Integer limit, Integer offset, Filtros filtros);

    List<Map<String, Object>> listarCarreras();

    List<Map<String, Object>> listarCategorias();

    List<Map<String, Object>> listarEstado();

    List<Map<String, Object>> listarRoles();

    Integer findIdRolById(Integer id);

    Boolean saveRol(Rol rol);

    Optional<Rol> findRolByCorreo(String correo);

    Integer findIdRolByName(String rol);
    
    Integer findIdRolByIdCarrera(Integer idCarrera);
    
    String findNameRolById(Integer idrol);

}
