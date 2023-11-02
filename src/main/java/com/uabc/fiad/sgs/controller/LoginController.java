package com.uabc.fiad.sgs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    /**
     * Regresa la vista para Iniciar sesión
     * *@return     Vista para Login
     */
    @GetMapping("/login")
    String login() {
        return "inicio-sesion";
    }

    /**
     * Regresa la vidtqa principal del sistema
     * @return      Vista principal
     */
    @GetMapping("/index")
    String incio() {
        return "index";
    }
}
