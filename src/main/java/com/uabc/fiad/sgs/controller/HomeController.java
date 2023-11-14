package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ISolicitudService solicitudService;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Muestra la pantalla inicial del sistema
     * @param model el modelo de spring
     * @return      la vista de la pantalla inicial, con sus respectivos atributos de modelo
     */
    @GetMapping("")
    public String home(Model model) {

        // Obtener lista de solicitudes
        Usuario u = SessionUtils.getUsuario(usuarioService);
        if (u == null) {
            return "redirect:/login";
        }

        List<Solicitud> solicitudes = solicitudService.findByUserId(u.getIdUsuario());

        model.addAttribute("solicitudes", solicitudes);

        return "index.html";
    }
}
