package com.uabc.fiad.sgs.security;

import com.uabc.fiad.sgs.entity.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {
    public UserDetails toUserDetails(Usuario usuario) {

        // TODO: Manejar roles, ahora mismo mapea todos los usuarios con el rol de docentes hardcodeado
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles("Docente")
//                .roles(usuario.getRoles().toArray(String[]::new))
                .build();
    }
}
