package com.uabc.fiad.sgs.Service;


import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
