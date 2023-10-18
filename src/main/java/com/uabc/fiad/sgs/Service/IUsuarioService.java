package com.uabc.fiad.sgs.Service;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    Optional<UsuarioDTO> findById(Integer id);

    List<UsuarioDTO> findAll();
}
