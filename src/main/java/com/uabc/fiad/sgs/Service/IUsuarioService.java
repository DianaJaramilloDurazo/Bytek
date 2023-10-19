package com.uabc.fiad.sgs.Service;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.Entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    Optional<UsuarioDTO> findById(Integer id);

    Optional<Usuario> findByCorreo(String correo);

    List<UsuarioDTO> findAll();

    Boolean save(Usuario usuario);
}
