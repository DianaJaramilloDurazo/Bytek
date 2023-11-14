package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("solicitud")
public class SolicitudController {

    @Autowired
    private ISolicitudService solicitudService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("")
    public String getSolicitud(Model model, @RequestParam("id") Integer id) {
        Optional<Solicitud> so = solicitudService.findById(id);

        if (so.isEmpty()) {
            return "";
        }

        Solicitud s = so.get();

        model.addAttribute("solicitud", s);

        model.addAttribute("usuario", usuarioService.findById(s.getIdUsuario()).get());

        return "fragments/solicitud/solicitud-detalles :: solicitud-detalles";
    }
}
