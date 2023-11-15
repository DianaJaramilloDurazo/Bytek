package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
