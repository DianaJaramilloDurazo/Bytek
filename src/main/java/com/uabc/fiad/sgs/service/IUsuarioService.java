package com.uabc.fiad.sgs.service;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    Optional<UsuarioDTO> findById(Integer id);

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNumEmpleado(Integer numEmpleado);

    List<UsuarioDTO> findAll();

    Boolean save(Usuario usuario);

    Integer TotalRecords();

    List<UsuarioDTO> pagination(Integer limit, Integer offset);
}