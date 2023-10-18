package com.uabc.fiad.sgs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registrar-usuario")
public class RegistrarUsuarioController {

    @GetMapping("/registrar")
    public String registrarUsuario() {
        return "ListarUsuarios.html";
    }

    @GetMapping("/get-registrar-form")
    public String getRegistrarUsuarioForm() {
        return "fragments/usuario/registrar-usuario-form :: registrar-usuario-form";
    }

    @PostMapping("/validar/correo")
    public String validarCorreo() {
        return "fragments/usuario/registrar-usuario-form :: correo(valid=false, value='valor')";
    }
}
