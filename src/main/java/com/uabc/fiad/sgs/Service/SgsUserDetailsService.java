package com.uabc.fiad.sgs.Service;

import com.uabc.fiad.sgs.Entity.Usuario;
import com.uabc.fiad.sgs.Security.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SgsUserDetailsService implements UserDetailsService {


    private final UsuarioService usuarioService;

    private final UserDetailsMapper userDetailsMapper;

    public SgsUserDetailsService(UsuarioService usuarioService, UserDetailsMapper userDetailsMapper) {
        this.usuarioService = usuarioService;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioService.findByCorreo(correo);

        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("No se encontró el usuario " + correo);
        }

        return userDetailsMapper.toUserDetails(usuario.get());
    }

}
