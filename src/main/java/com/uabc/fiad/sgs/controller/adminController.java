package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/registro-rol")
    String login() {
        return "registroRol";
    }

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
        return "<p>hola</p>";
    }
}
