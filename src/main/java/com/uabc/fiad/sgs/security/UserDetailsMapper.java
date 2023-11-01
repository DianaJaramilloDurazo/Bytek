package com.uabc.fiad.sgs.security;

import com.uabc.fiad.sgs.entity.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {
    public UserDetails toUserDetails(Usuario usuario, String rol) {

        // TODO: Manejar roles, ahora mismo mapea todos los usuarios con el rol de docentes hardcodeado
        return User.withUsername(usuario.getCorreo())
                .password(usuario.getPassword())
                .roles(rol)
//                .roles(usuario.getRoles().toArray(String[]::new))
                .build();
    }
}
