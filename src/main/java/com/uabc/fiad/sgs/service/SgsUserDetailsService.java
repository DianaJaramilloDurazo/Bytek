package com.uabc.fiad.sgs.service;

import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.security.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class SgsUserDetailsService implements UserDetailsService {


    private final UsuarioService usuarioService;

    private final UserDetailsMapper userDetailsMapper;

    public SgsUserDetailsService(UsuarioService usuarioService, UserDetailsMapper userDetailsMapper) {
        this.usuarioService = usuarioService;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Del usuario, se elimina lo que se haya puesto luego del @ y se le agrega el dominio, se usa este correo para
        // buscar el usuario
        String correo = username.split("@", 2)[0] + "@uabc.edu.mx";

        Optional<Usuario> oUsuario = usuarioService.findByCorreo(correo);

        if (oUsuario.isEmpty()) {
            throw new UsernameNotFoundException("No se encontró el usuario " + correo);
        }

        Usuario usuario = oUsuario.get();

        String rolAsignado = "DOCENTE";

        if (usuario.getIdRol() != 0) {
            Optional<Rol> rol = usuarioService.findRolByCorreo(correo);
            if(rol.isEmpty()){
                throw new UsernameNotFoundException("El usuario '" + correo + "' tiene asignado un rol inexistente");
            } else {
                rolAsignado = rol.get().getRol().toUpperCase();
            	StringTokenizer nombreRol = new StringTokenizer(rol.get().getRol().toUpperCase());
                rolAsignado = nombreRol.nextToken();
                //System.out.println(rolAsignado);
            }
        }

        if(usuario.getIdEstado() != 1){
            throw new UsernameNotFoundException("El usuario no esta activo ");
        }

        return userDetailsMapper.toUserDetails(usuario, rolAsignado);
    }

}
