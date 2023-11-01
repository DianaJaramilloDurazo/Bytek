package com.uabc.fiad.sgs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    String login() {
        return "inicio-sesion";
    }

    @GetMapping("/index")
    String incio() {
        return "index";
    }
}
