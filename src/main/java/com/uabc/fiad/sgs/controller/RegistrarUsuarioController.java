package com.uabc.fiad.sgs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registrar-usuario")
public class RegistrarUsuarioController {

    @GetMapping("/registrar")
    public String getRegistrarUsuario() {
        return "ListarUsuarios.html";
    }

    @PostMapping("/registrar")
    public String registrarUsuario() {
        return "";
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
