package com.uabc.fiad.sgs.service;

import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.security.UserDetailsMapper;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Del usuario, se elimina lo que se haya puesto luego del @ y se le agrega el dominio, se usa este correo para
        // buscar el usuario
        String correo = username.split("@", 2)[0] + "@uabc.edu.mx";

        Optional<Usuario> usuario = usuarioService.findByCorreo(correo);
        Optional<Rol> rol = usuarioService.findRolByCorreo(correo);
        String rolAsignado = "DOCENTE";

        if (usuario.isEmpty()) {
            System.out.println(usuarioService.findRolByCorreo(correo));
            if(rol.isEmpty()){
                throw new UsernameNotFoundException("No se encontró el usuario " + correo);
            }else{
                usuario = usuarioService.findRolById(rol.get().getIdRol());
                usuario.get().setPassword(rol.get().getPassword());
                System.out.println(usuario);
                rolAsignado = rol.get().getRol().toUpperCase();
            }
        }
        if(usuario.get().getIdEstado() != 1){
            throw new UsernameNotFoundException("El usuario no esta activo ");
        }
        if(usuarioService.findIdRolById(usuario.get().getIdUsuario()) != 0){
            System.out.println("Tiene rol");
        }

        return userDetailsMapper.toUserDetails(usuario.get(),rolAsignado);
    }

}
