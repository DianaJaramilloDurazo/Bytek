package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.service.MailManager;
import com.uabc.fiad.sgs.utils.SessionUtils;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("solicitud")
public class SolicitudController {

    @Autowired
    private ISolicitudService solicitudService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MailManager mailManager;

    @GetMapping("por-firmar")
    public String getSolicitudesPorFirmar(Model model) {
        Usuario u = SessionUtils.getUsuario(usuarioService);
        if (u == null) {
            return "redirect:/login";
        }

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

        return "fragments/solicitud/solicitudes-por-firmar :: solicitudes-por-firmar";
    }

    @GetMapping("")
    public String getSolicitud(Model model, @RequestParam("id") Integer id) {

        Optional<Solicitud> so = solicitudService.findById(id);

        if (so.isEmpty()) {
            return "";
        }

        Solicitud s = so.get();

        model.addAttribute("solicitud", s);

        Usuario u = usuarioService.findById(s.getIdUsuario()).get();
        model.addAttribute("usuario", u);

        List<Map<String, Object>> categorias = usuarioService.listarCategorias();

        String categoria = "";
        for (Map<String, Object> c : categorias) {
            if (c.get("idCategoria").equals(u.getIdCategoria())) {
                categoria = (String) c.get("Cat_Descripcion");
            }
        }

        model.addAttribute("categoria", categoria);

        List<Map<String, Object>> carreras = usuarioService.listarCarreras();

        String carrera = "";
        for (Map<String, Object> c : carreras) {
            if (c.get("idCarrera").equals(s.getIdCarrera())) {
                carrera = (String) c.get("Carrera_Nombre");
            }
        }

        model.addAttribute("carrera", carrera);

        model.addAttribute("recursos", solicitudService.listarRecursos(id));

        model.addAttribute("actividades", solicitudService.listarActividadesAsociadas(id));

        model.addAttribute("firmas", solicitudService.listarFirmas(id));

        return "fragments/solicitud/solicitud-detalles :: solicitud-detalles";
    }

    @PostMapping(value = "firmar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    @HxTrigger("refreshSolicitudes")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String firmarSolicitud(@RequestParam Integer solicitudid) {

        Usuario u = SessionUtils.getUsuario(usuarioService);
        if (u == null) {
            return "redirect:/login";
        }

        boolean success = solicitudService.firmarSolicitud(solicitudid, u.getIdUsuario(), usuarioService.findIdRolById(u.getIdUsuario()));

        if (!success) {
            return "<div class='alert alert-danger' role='alert'> Ocurrió un error al firmar la solicitud </div>";
        }

        // Envíar correos
        Optional<Solicitud> s = solicitudService.findById(solicitudid);

        if (s.isEmpty()) {
            return "<div class='alert alert-warning' role='alert'> La solicitud fue firmada con éxito, pero ocurrió un error al notificar a sobre la firma </div>";
        }

        Solicitud solicitud = s.get();

        List<String> correos = solicitudService.obtnerCorrreosFirmas(solicitudid);

        Optional<Usuario> usuarioFirmaO = usuarioService.findById(solicitud.getIdUsuario());

        if (usuarioFirmaO.isEmpty()) {
            return "<div class='alert alert-warning' role='alert'> La solicitud fue firmada con éxito, pero ocurrió un error al notificar a sobre la firma </div>";
        }

        Usuario usuarioFirma = usuarioFirmaO.get();

        correos.add(usuarioFirma.getCorreo());
        // TODO: Descomentar la siguiente línea para que envíe los correos
//        mailManager.firmada(correos, solicitud.getNombreEvento(), usuarioService.findNameRolById(u.getIdRol()));

//        List<String> c = new ArrayList<>();
//        c.add(usuarioFirma.getCorreo());
//        mailManager.firmada(c, solicitud.getNombreEvento(), usuarioService.findNameRolById(u.getIdRol()));

        return "<div class='alert alert-success' role='alert'> La solicitud fue firmada con exito y se notificó por correo </div>";
    }
}