package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.service.UsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/docente")
public class DocenteController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private ISolicitudService solicitudService;

	/**
	 * Obtiene el cuerpo del formulario de registrar una solicitud
	 * 
	 * @param model modelo utilizado para pasar datos a la vista
	 * @return fragmento de thymeleaf con el cuerpo del formulario
	 */
	@GetMapping("/get-registrar-solicitud-form")
	public String getForm(Model model) {
		System.out.println("Golaa");
		model.addAttribute("carreras", usuarioService.listarCarreras());
		return "fragments/solicitud/registrar-solicitud-form :: registrar-solicitud-form";
	}

	/**
	 * Guarda una solictud de salida Generada por el docente
	 * 
	 * @param solicitud     solicitud a guardar, con algunos datos
	 * @param fechaSalida   fecha de salida
	 * @param fechaRegreso  fecha de regreso
	 * @param horaSalida    hora de salida
	 * @param horaRegreso   hora de regreso
	 * @param recursos      recursos a utilizar para la salida
	 * @param actividades   actividades a realziar en la salida
	 * @param numPasajeros  numero de pasajero en caso de seleccionar transporte
	 * @param litros        listros de gasolina en caso de seleccionar combustible
	 * @param otroRecurso   otro recurso en caso de que se seleccione
	 * @param otroActividad otra actividad en caso de ser seleccionada
	 * @return mensaje que indica si se registro o no la solicitud
	 */
	@PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	@HxTrigger("resetForm")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String registrarSolicitud(Solicitud solicitud,
			@RequestParam(value = "fSalida") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaSalida,
			@RequestParam(value = "fRegreso") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaRegreso,
			@DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaSalida,
			@DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaRegreso,
			@RequestParam(value = "recursos") List<Integer> recursos,
			@RequestParam(value = "actividades") List<Integer> actividades,
			@RequestParam(value = "transporte", required = false) String numPasajeros,
			@RequestParam(value = "combustible", required = false) Integer litros,
			@RequestParam(value = "otroRecurso", required = false) String otroRecurso,
			@RequestParam(value = "otroActividad", required = false) String otroActividad) {

		// Se obtiene el usuario de la sesión actual
		Usuario u = SessionUtils.getUsuario(usuarioService);
		// Se obtine su id
		solicitud.setIdUsuario(u.getIdUsuario());

		// Se guarda la fehca y hora
		solicitud.setFechaSalida(fechaSalida.atTime(horaSalida));
		solicitud.setFechaRegreso(fechaRegreso.atTime(horaRegreso));

		// Se guarda la solicitud y se obtiene su id
		Integer idSolicitud = solicitudService.saveSolicitud(solicitud);
		
		// Se verifica si la solicitud fue guardada
		if (idSolicitud != 0) {
			// Registrar Recursos
			// Se intera sobre los recursos que fueron seleccionados
			for (Integer recurso : recursos) {

				// En caso de que se haya selccionado Transporte se registra su detalle
				if (recurso == 2) {
					solicitudService.saveRecurso(idSolicitud, recurso, numPasajeros);

					// En caso de que se haya selccionado Otro se registra su detalle
				} else if (recurso == 5) {
					solicitudService.saveRecurso(idSolicitud, recurso, otroRecurso);

					// Coso contrario se registra solo el recurso
				} else {
					solicitudService.saveRecurso(idSolicitud, recurso, null);
				}
			}
			// Se intera sobre las actividades que fueron seleccionadas
			for (Integer actividad : actividades) {

				// En caso de que se haya selccionado Otra se registra su detalle
				if (actividad == 6) {
					solicitudService.saveActividad(idSolicitud, actividad, otroActividad);
				} else {
					// Coso contrario se registra solo la actividad
					solicitudService.saveActividad(idSolicitud, actividad, null);
				}
			}

			// Registro de firmas requeridas
			List<Integer> firmas = new ArrayList<>();
			firmas.add(usuarioService.findIdRolByName("Administración"));

			// Obtener Rol del usuario para saber que firmas requiere
			Integer rolUsuario = usuarioService.findIdRolById(u.getIdUsuario());
			if (rolUsuario != 0) {
				String nombreRol = usuarioService.findNameRolById(rolUsuario);

				if (nombreRol.equalsIgnoreCase("Director")) {
					firmas.add(usuarioService.findIdRolByName("Subdirector"));
					firmas.add(usuarioService.findIdRolByIdCarrera(solicitud.getIdCarrera()));
				} else if (nombreRol.equalsIgnoreCase("Subdirector")) {
					firmas.add(usuarioService.findIdRolByName("Director"));
					firmas.add(usuarioService.findIdRolByIdCarrera(solicitud.getIdCarrera()));
				} else {
					// Es un coordinador
					firmas.add(usuarioService.findIdRolByName("Director"));
					firmas.add(usuarioService.findIdRolByName("Subdirector"));
					// Verificar si va dirigido a otra carrea que no es la suya
					if (u.getIdCarrera() != solicitud.getIdCarrera()) {
						// Si no es la misma carrera se pide la firma del coordinador de la otra carrera
						firmas.add(usuarioService.findIdRolByIdCarrera(solicitud.getIdCarrera()));
					}
				}

			} else {
				// Es un docente ocupa todas las firmas
				firmas.add(usuarioService.findIdRolByName("Director"));
				firmas.add(usuarioService.findIdRolByName("Subdirector"));
				firmas.add(usuarioService.findIdRolByIdCarrera(solicitud.getIdCarrera()));
			}
			solicitudService.saveFirmas(idSolicitud, firmas);
			System.out.println("Firmas necesarias");
			System.out.println(firmas);

			return "<div class='alert alert-success' role='alert'> La solicitud fue creada </div>";
		}		
		return "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al crear la solicitud </div>>";
	}

}
