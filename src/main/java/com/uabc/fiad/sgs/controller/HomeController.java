package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Solicitud;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * Muestra la pantalla inicial del sistema
     * @param model el modelo de spring
     * @return      la vista de la pantalla inicial, con sus respectivos atributos de modelo
     */
    @GetMapping("")
    public String home(Model model) {

        // Solicitudes de prueba
        // Obtener lista de solicitudes
        ArrayList<Solicitud> solicitudes = new ArrayList<>();
        solicitudes.add(new Solicitud(
                1,
                "Visita a SoftTek",
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now(),
                500.0f,
                "SoftTek",
                null,
                1,
                1,
                "Pendiente"
        ));

        solicitudes.add(new Solicitud(
                2,
                "Reunión con Clientes",
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now(),
                300.0f,
                "Clientes",
                null,
                2,
                2,
                "Firma Parcial"
        ));

        // Tercera solicitud
        solicitudes.add(new Solicitud(
                3,
                "Capacitación Interna",
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now(),
                200.0f,
                "Departamento de Recursos Humanos",
                null,
                3,
                3,
                "Reporte Pendiente"
        ));

        model.addAttribute("solicitudes", solicitudes);

        return "index.html";
    }
}
