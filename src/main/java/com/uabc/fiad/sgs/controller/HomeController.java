package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.DTO.UsuarioDTO;
import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.FiltrosSolicitudes;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ISolicitudService solicitudService;

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Muestra la pantalla inicial del sistema
	 * @param filtros 	filtros que se usarán para buscar solicitudes
     * @param model 	el modelo de spring
     * @return      	la vista de la pantalla inicial, con sus respectivos atributos de modelo
     */
    @GetMapping("")
    public String home(@ModelAttribute Filtros filtros,Model model) {
		model.addAttribute("filtros", filtros);
		// Obtener lista de solicitudes
		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}

		if (SessionUtils.getUserDetails().getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_DOCENTE"))) {

			List<Solicitud> solicitudes = solicitudService.findByUserId(u.getIdUsuario());

			model.addAttribute("solicitudes", solicitudes);
		} else {
			// Manejar filtros
			model.addAttribute("filtros", filtros);

			// Tamaño de página: cuántos registros se mostrarán por página
			int pageSize = 4;
			filtros.setPage(1);
			// Calcular el desplazamiento (offset) para determinar desde qué registro se
			// debe iniciar en la base de datos
			int offset = (filtros.getPage() - 1) * pageSize;

			// Obtener total de registros
			Integer totalRecords = solicitudService.totalSolicitudesPendientes(usuarioService.findIdRolById(u.getIdUsuario()));

			// Calcular el número total de páginas (totalPages) usando una división entera
			int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
			// Define el número máximo de páginas a mostrar en la barra de paginación
			int maxPagesToShow = 4;

			// Calcula el rango de páginas a mostrar
			int startPage = Math.max(1, filtros.getPage() - maxPagesToShow / 2);
			int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

			// Crear una lista de números de página para mostrar en la interfaz de usuario
			List<Integer> pages = IntStream.rangeClosed(startPage, endPage).boxed().toList();
			model.addAttribute("first", startPage);
			model.addAttribute("pages", pages);
			model.addAttribute("current", filtros.getPage());
			model.addAttribute("next", filtros.getPage() + 1);
			model.addAttribute("prev", filtros.getPage() - 1);
			model.addAttribute("end", endPage);
			model.addAttribute("last", totalPages);
			List<Solicitud> solicitudesPendientes = solicitudService.PaginacionSolicitudesPendientes(pageSize, offset,
					filtros, usuarioService.findIdRolById(u.getIdUsuario()));
			model.addAttribute("solicitudes_pendientes", solicitudesPendientes);
			// También los usuarios registrados
			// todo: Monitorear por problemas de rendimiento cargando la página de inicio,
			// simplemente es la solución más
			// sencilla que se me ocurrió
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


	/**
	 * Regresa la vista de historial de  solicitudes
	 * @param model     el modelo utilizado para pasar datos a la vista
	 * @return          La vista para ver solicitudes realizadas
	 */
	@HxTrigger("refresh")
	@GetMapping("/historialDeSolicitudes")
	public String listarUsuario(Model model) {
		int page = 1;
		FiltrosSolicitudes filtros = new FiltrosSolicitudes();
		filtros.setPage(page);
	//        model.addAttribute("current", page);
		model.addAttribute("filtros", filtros);
		return "HistorialSolicitudes";
    }
	

	
	@GetMapping("/listaSolicitudesRealizadas")
    public String listaSolictudesRealizadas(@ModelAttribute FiltrosSolicitudes filtros, Model model) {
    	 // Obtener lista de solicitudes
        Usuario u = SessionUtils.getUsuario(usuarioService);
        if (u == null) {
            return "redirect:/login";
        }

		model.addAttribute("filtros", filtros);

		// Tamaño de página: cuántos registros se mostrarán por página
		int pageSize = 10;

		// Calcular el desplazamiento (offset) para determinar desde qué registro se debe iniciar en la base de datos
		int offset = (filtros.getPage() - 1) * pageSize;

		// Define el número máximo de páginas a mostrar en la barra de paginación
		int maxPagesToShow = 4;

		int totalRecords;

		List<Solicitud> solicitudesRealizadas;
		List<Usuario> usuarios;
		if (SessionUtils.getUserDetails().getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_DOCENTE"))) {

			//Obtener total de registros
			totalRecords = solicitudService.totalSolicitudesRealizadasById(u.getIdUsuario(), filtros);

			// Calcular el número total de páginas (totalPages) usando una división entera
			int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

			// Calcula el rango de páginas a mostrar
			int startPage = Math.max(1, filtros.getPage() - maxPagesToShow / 2);
			int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

			// Crear una lista de números de página para mostrar en la interfaz de usuario
			List<Integer> pages = IntStream.rangeClosed(startPage, endPage).boxed().toList();
			model.addAttribute("first", startPage);
			model.addAttribute("pages", pages);
			model.addAttribute("current", filtros.getPage());
			model.addAttribute("next", filtros.getPage() + 1);
			model.addAttribute("prev", filtros.getPage() - 1);
			model.addAttribute("end", endPage);
			model.addAttribute("last", totalPages);

			solicitudesRealizadas = solicitudService.paginarSolicitudesRealizadasById(u.getIdUsuario(), filtros, pageSize, offset);
			usuarios = new ArrayList<>();
			for (Solicitud s : solicitudesRealizadas) {
				usuarios.add(usuarioService.findById(s.getIdUsuario()).get());
			}
		}else{

			totalRecords = solicitudService.totalSolicitudesRealizadas(usuarioService.findIdRolById(u.getIdUsuario()), filtros);

			// Calcular el número total de páginas (totalPages) usando una división entera
			int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

			// Calcula el rango de páginas a mostrar
			int startPage = Math.max(1, filtros.getPage() - maxPagesToShow / 2);
			int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

			// Crear una lista de números de página para mostrar en la interfaz de usuario
			List<Integer> pages = IntStream.rangeClosed(startPage, endPage).boxed().toList();
			model.addAttribute("first", startPage);
			model.addAttribute("pages", pages);
			model.addAttribute("current", filtros.getPage());
			model.addAttribute("next", filtros.getPage() + 1);
			model.addAttribute("prev", filtros.getPage() - 1);
			model.addAttribute("end", endPage);
			model.addAttribute("last", totalPages);

			// Lista de solicitudes para firmar
			solicitudesRealizadas = solicitudService.paginarSolicitudesRealizadas(usuarioService.findIdRolById(u.getIdUsuario()), filtros, pageSize, offset);
			// También los usuarios registrados
			// todo: Monitorear por problemas de rendimiento cargando la página de inicio, simplemente es la solución más
			//  sencilla que se me ocurrió
			usuarios = new ArrayList<>();
			for (Solicitud s : solicitudesRealizadas) {
				usuarios.add(usuarioService.findById(s.getIdUsuario()).get());
			}
		}

		model.addAttribute("solicitudes_realizadas", solicitudesRealizadas);
		model.addAttribute("usuarios", usuarios);

		model.addAttribute("categorias", usuarioService.listarCategorias());

		List<String> actAsociadas = new ArrayList<>();
		actAsociadas.add("CACEI");
		actAsociadas.add("Licenciatura");
		actAsociadas.add("Personal");
		actAsociadas.add("ISO");
		actAsociadas.add("Posgrado");

		model.addAttribute("actAsociadas", actAsociadas);

		model.addAttribute("carreras", usuarioService.listarCarreras());

		model.addAttribute("estados", solicitudService.listarEstados());
    	return "HistorialSolicitudes :: listaSolicitudesRealizadas";
    }
	
	
}
