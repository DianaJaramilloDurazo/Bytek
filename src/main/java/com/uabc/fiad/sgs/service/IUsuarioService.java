package com.uabc.fiad.sgs.service;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNumEmpleado(Integer numEmpleado);

    List<UsuarioDTO> findAll();

    Boolean save(Usuario usuario);

    Integer TotalRecords();

    List<UsuarioDTO> pagination(Integer limit, Integer offset);

    List<Map<String, Object>> listarCarreras();

    List<Map<String, Object>> listarCategorias();

    List<Map<String, Object>> listarEstado();
}
