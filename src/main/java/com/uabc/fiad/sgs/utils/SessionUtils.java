package com.uabc.fiad.sgs.utils;

import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Clase para sostener métodos de utilidad para trabajar con la sesión.
 * Todos estos métodos usan la sesión iniciada, por ejemplo, getUserDetails()
 * regresa el objeto UserDetails que guarda Spring Security en la sesión actual
 */
public class SessionUtils {

    /**
     * Regresa el objeto UserDetails de Spring security para la sesión actual, nulo si no hay
     * @return  UserDetails del usuario con sesión activa
     */
    public static UserDetails getUserDetails() {

        // Obtener el contexto de seguridad
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        System.out.println(authentication.getAuthorities());

        // Si la autenticación no es nula, obtener los detalles del principal
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof UserDetails ? (UserDetails) principal : null;
        }

        // Si es nula regresar null
        return null;
    }

    /**
     * Regresa la entidad del usuario con la sesión iniciada, o null si no tiene sesión
     * @return  entidad Usuario guardada en la sesión
     */
    public static Usuario getUsuario(IUsuarioService iUsuarioService) {

        // Primero obtener los detalles de usuario
        UserDetails ud = getUserDetails();

        // Si son nulos regresar null
        if (ud == null) {
            return null;
        }

        // Si no lo son, regresar el objeto de usuario obtenido de la base de datos por el correo
        Optional<Usuario> uo = iUsuarioService.findByCorreo(ud.getUsername());
        return uo.orElse(null);
    }
}
