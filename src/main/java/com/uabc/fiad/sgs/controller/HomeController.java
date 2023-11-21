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

        if (SessionUtils.getUserDetails().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCENTE"))) {

            List<Solicitud> solicitudes = solicitudService.findByUserId(u.getIdUsuario());

            model.addAttribute("solicitudes", solicitudes);
        } else {

            // Si tiene rol
            // Lista de solicitudes para firmar
            List<Solicitud> solicitudesPendientes = solicitudService.listarSolicitudesPendientes(usuarioService.findIdRolById(u.getIdUsuario()));

            // También los usuarios registrados
            // todo: Monitorear por problemas de rendimiento cargando la página de inicio, simplemente es la solución más
            //  sencilla que se me ocurrió
            List<Usuario> usuarios = new ArrayList<>();
            for (Solicitud s : solicitudesPendientes) {
                usuarios.add(usuarioService.findById(s.getIdUsuario()).get());
            }

            model.addAttribute("solicitudes_pendientes", solicitudesPendientes);
            model.addAttribute("usuarios_firmar", usuarios);
        }


        return "index.html";
    }
    
    @GetMapping("/listaSolicitudes")
    public String listaSolictudes(Model model) {
    	 // Obtener lista de solicitudes
        Usuario u = SessionUtils.getUsuario(usuarioService);
        if (u == null) {
            return "redirect:/login";
        }
        if (SessionUtils.getUserDetails().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCENTE"))) {

            List<Solicitud> solicitudes = solicitudService.findByUserId(u.getIdUsuario());

            model.addAttribute("solicitudes", solicitudes);
        } else {

            // Si tiene rol
            // Lista de solicitudes para firmar
            List<Solicitud> solicitudesPendientes = solicitudService.listarSolicitudesPendientes(usuarioService.findIdRolById(u.getIdUsuario()));

            // También los usuarios registrados
            // todo: Monitorear por problemas de rendimiento cargando la página de inicio, simplemente es la solución más
            //  sencilla que se me ocurrió
            List<Usuario> usuarios = new ArrayList<>();
            for (Solicitud s : solicitudesPendientes) {
                usuarios.add(usuarioService.findById(s.getIdUsuario()).get());
            }

            model.addAttribute("solicitudes_pendientes", solicitudesPendientes);
            model.addAttribute("usuarios_firmar", usuarios);
        }

    	return "index :: listaSolicitudes";
    }
}
