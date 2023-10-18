package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.Entity.Usuario;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registrar-usuario")
public class RegistrarUsuarioController {

    @GetMapping("/registrar")
    public String getRegistrarUsuario() {
        return "ListarUsuarios.html";
    }

    @PostMapping(value = "/registrar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String registrarUsuario(Usuario usuario) {
        System.out.println(usuario);
        
        return "<span class='text-success'>Usuario registrado correctamente</span>";
    }

    @GetMapping("/get-registrar-form")
    public String getRegistrarUsuarioForm() {
        return "fragments/usuario/registrar-usuario-form :: registrar-usuario-form";
    }

    @PostMapping("/validar/correo")
    public String validarCorreo(@RequestParam(value="correo") String correo) {
        // Obtener el valor del correo sin el nombre del parámetro
//        String correo_val = correo.split("=")[1];

        // Validar el correo
        boolean valid = correo.endsWith("@uabc.edu.mx");

        return "fragments/usuario/registrar-usuario-form :: correo(valid=" + valid + ", value='" + correo + "')";
    }
}
