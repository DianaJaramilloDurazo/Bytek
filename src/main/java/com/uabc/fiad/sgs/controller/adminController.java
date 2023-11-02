package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Regresa la vista para administrar cuentas y genera una paginación con una lista de usuarios
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          La vista para administrar cuentas
     */
    @HxTrigger("refresh")
    @GetMapping("/administrarCuenta")
    public String listarUsuario(Model model) {
        int page = 1;
        Filtros filtros = new Filtros();
        filtros.setPage(page);
//        model.addAttribute("current", page);
        model.addAttribute("filtros", filtros);
        return "ListarUsuarios";
    }

    /**
     * Obtiene una página de usuarios paginada.
     * @param filtros   los filstros aplicados, número de página, nombre, etc.
     * @param model     el modelo utilizado para pasar datos a la vista
     * @return          fragmento para mostrar la lista de usuarios
     */

    /**
     * Regresa una vista para registrar roles
     * @return  Vista para el registro del roles
     */
    @GetMapping("/registro-rol")
    String login() {
        return "registroRol";
    }

    /**
     * Método de POST para registrar un Rol. Mapea el cuerpo de la petición a un objeto de tipo Rol
     * @param rol    Objeto rol a registrar
     * @return       html plano con un mensaje de estado, si se registró correctamente o no
     */
    @PostMapping(value = "/registrar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String registrarUsuario(Rol rol) {
        System.out.println(rol);
        String rawPwd =rol.getPassword();

        // Encriptar la contraseña
        String encodedPwd = passwordEncoder.encode(rawPwd);

        // Cambiar la contraseña por la encriptada
        rol.setPassword(encodedPwd);

        // Guardar el usuario
        Boolean res = usuarioService.saveRol(rol);
        if(res){
            return "<p>Rol registrado correctamente</p>";
        }
        return "<p>Error al registrar  el Rol</p>";
    }
}
