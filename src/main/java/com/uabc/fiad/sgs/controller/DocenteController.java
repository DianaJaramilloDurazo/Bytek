package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.service.MailManager;
import com.uabc.fiad.sgs.service.UsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
	
	@Autowired
	private MailManager mailManager;

	/**
	 * Obtiene el cuerpo del formulario de registrar una solicitud
	 * 
	 * @param model modelo utilizado para pasar datos a la vista
	 * @return fragmento de thymeleaf con el cuerpo del formulario
	 */
	@GetMapping("/get-registrar-solicitud-form")
	public String getForm(Model model) {
		Usuario u= SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}
		
		List<Map<String, Object>> categorias = usuarioService.listarCategorias();
		String categoria = "";
		for (Map<String, Object> c : categorias) {
			if (c.get("idCategoria").equals(u.getIdCategoria())) {
				categoria = (String) c.get("Cat_Descripcion");
			}
		}
		model.addAttribute("categoria", categoria);

		model.addAttribute("usuario", u);
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
	@HxTrigger("refreshSolicitudes")
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
		
		if (u == null) {
			return "redirect:/login";
		}
		// Se obtine su id
		solicitud.setIdUsuario(u.getIdUsuario());
		System.out.print(solicitud);
		System.out.print(horaSalida);
		// Se guarda la fehca y hora
		solicitud.setFechaSalida(fechaSalida.atTime(horaSalida));
		solicitud.setFechaRegreso(fechaRegreso.atTime(horaRegreso));

		// Se guarda la solicitud y se obtiene su id
		Integer idSolicitud = solicitudService.saveSolicitud(solicitud);

		// Se verifica si la solicitud fue guardada
		if (idSolicitud != 0) {
			// Registrar Recursos
			// Se intera sobre los recursos que fueron seleccionados
			Boolean recursoRegistrado;
			Boolean resultRecurso = true;
			for (Integer recurso : recursos) {
				
				// En caso de que se haya selccionado Transporte se registra su detalle
				if (recurso == 2) {
					recursoRegistrado = solicitudService.saveRecurso(idSolicitud, recurso, numPasajeros);

					// En caso de que se haya selccionado Otro se registra su detalle
				} else if (recurso == 5) {
					recursoRegistrado = solicitudService.saveRecurso(idSolicitud, recurso, otroRecurso);

					// Coso contrario se registra solo el recurso
				} else {
					recursoRegistrado = solicitudService.saveRecurso(idSolicitud, recurso, null);
				}
				if(!recursoRegistrado) {
					resultRecurso = false;
				}
			}
			// Se intera sobre las actividades que fueron seleccionadas
			Boolean actividadRegistrada;
			Boolean resultActividad = true;
			for (Integer actividad : actividades) {

				// En caso de que se haya selccionado Otra se registra su detalle
				if (actividad == 6) {
					actividadRegistrada = solicitudService.saveActividad(idSolicitud, actividad, otroActividad);
				} else {
					// Coso contrario se registra solo la actividad
					actividadRegistrada = solicitudService.saveActividad(idSolicitud, actividad, null);
				}
				
				if(!actividadRegistrada) {
					resultActividad = false;
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
					Integer idCarreraRol = usuarioService.findIdCarreraByIdRol(rolUsuario);
					// Verificar si va dirigido a otra carrea que no es la suya
					//u.getIdCarrera()
					if (idCarreraRol != solicitud.getIdCarrera()) {
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
			
			String resultadoFinal="";
			if(resultActividad && resultRecurso) {
				resultadoFinal = "<div class='alert alert-success' role='alert'> La solicitud fue creada </div>";
			}else {
				if(!resultActividad) {
					resultadoFinal += "<div class='alert alert-danger' role='alert'>La solictud fue creada, pero ha ocurrido un error al registrar las actividades</div>";
				}
				if(!resultRecurso) {
					resultadoFinal += "<div class='alert alert-danger' role='alert'>La solictud fue creada, pero, ha ocurrido un error al registrar los recursos </div>";
				}
			}
			String nombreUsuario = u.getUsername()+" " + u.getApPaterno() + " " + u.getApMaterno();
			List<Map<String, Object>> rolesFirmas = solicitudService.DatosRolesFirma(idSolicitud);
			System.out.println(nombreUsuario);
			System.out.println(rolesFirmas);
			// Se comento el envio de correos, para no mandar correos a usuarios reales
			//mailManager.solicitarFirmas(rolesFirmas, nombreUsuario, solicitud.getNombreEvento());

			return  resultadoFinal;
		}
		return "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al crear la solicitud </div>";
	}

	/**
	 * Cancelar una solictud
	 * 
	 * @param idSolicitud id de la solcitud a cancelar
	 * @return Respuesta sobre si se cancelo la solicitud o no
	 */
	@PostMapping(value = "/cancelar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	@HxTrigger("refreshSolicitudes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String CancelarSolicitud(@RequestParam(value = "solicitudCancelar", required = false) Integer idSolicitud) {

		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}
		if (idSolicitud != null) {
			Boolean cancelada = solicitudService.cancelarSolicitud(idSolicitud);
			if (cancelada) {
				return "<div class='alert alert-success' role='alert'> La solicitud fue cancelada </div>";
			}
		}
		return "<div class='alert alert-danger' role='alert'> Ha ocurrido un error al intentar cancelar la solcitud </div>";
	}

	/**
	 *
	 * @param idSolicitud	id de la solicitud a mostrar su información a editar
	 * @param model			modelo utilizado para pasar datos a la vista
	 * @return				fragmento para mostrar la información de la solicitud a editar
	 */
	@HxTrigger("resetForm")
	@GetMapping("/get-editar-form")
	public String editarSolicitud(@RequestParam(value = "id") Integer idSolicitud, Model model) {

		Usuario u= SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}
		List<Map<String, Object>> categorias = usuarioService.listarCategorias();
		String categoria = "";
		for (Map<String, Object> c : categorias) {
			if (c.get("idCategoria").equals(u.getIdCategoria())) {
				categoria = (String) c.get("Cat_Descripcion");
			}
		}
		model.addAttribute("categoria", categoria);

		model.addAttribute("usuario", u);
		

		Solicitud solicitud = solicitudService.findById(idSolicitud).get();
		model.addAttribute("solicitud", solicitud);
		LocalDate fechaSalida = solicitud.getFechaSalida().toLocalDate();
		LocalDate fechaRegreso = solicitud.getFechaRegreso().toLocalDate();
		LocalTime horaSalida = solicitud.getFechaSalida().toLocalTime();
		LocalTime horaRegreso = solicitud.getFechaRegreso().toLocalTime();

		model.addAttribute("fechaSalida", fechaSalida);
		model.addAttribute("fechaRegreso", fechaRegreso);
		model.addAttribute("horaRegreso", horaRegreso);
		model.addAttribute("horaSalida", horaSalida);

		model.addAttribute("recursos", solicitudService.listarRecursos(idSolicitud));
		model.addAttribute("actividades", solicitudService.listarActividadesAsociadas(idSolicitud));
		model.addAttribute("carreras", usuarioService.listarCarreras());
		System.out.println(solicitudService.listarRecursos(idSolicitud));
		return "fragments/solicitud/editar-solicitud :: editar-solicitud";
	}


	/**
	 * Método POST para editar una solicitud de salida
	 * @param solicitud			solicitud de salida a editar
	 * @param fechaSalida		fecha de salida al evento
	 * @param fechaRegreso		fecha de regreso del evento
	 * @param horaSalida		hora de salida al evento
	 * @param horaRegreso		hora de regreso del evento
	 * @param recursos			lista con los id de los recursos a solicitar
	 * @param actividades		lista con los id de la actividad o actividades relacionadas
	 * @param numPasajeros		número de pasajeros si es que se solicita transporte
	 * @param otroRecurso		especificación de otro recurso si es que se solicita
	 * @param otroActividad		especificación de otra actividad si es que se soliciat
	 * @return					notificación sobre el resulatdo de la edición de la solicitud
	 */
	@PostMapping(value = "/editar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	@HxTrigger("refreshSolicitudes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String editarSolicitud(Solicitud solicitud,
			@RequestParam(value = "fSalida") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaSalida,
			@RequestParam(value = "fRegreso") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaRegreso,
			@DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaSalida,
			@DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaRegreso,
			@RequestParam(value = "recursosEditar") List<Integer> recursos,
			@RequestParam(value = "actividadesEditar") List<Integer> actividades,
			@RequestParam(value = "Transporte", required = false) String numPasajeros,
			@RequestParam(value = "Otro", required = false) String otroRecurso,
			@RequestParam(value = "Otra", required = false) String otroActividad){
		
		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}
		// Guarda id y fechas dentro de la solciitud a editar
		Integer idSolicitud = solicitud.getIdSolicitud();
		solicitud.setFechaSalida(fechaSalida.atTime(horaSalida));
		solicitud.setFechaRegreso(fechaRegreso.atTime(horaRegreso));
		Boolean editado = solicitudService.updateSolicitud(solicitud);
		
		//mailManager.sendMessaje("omar.herrera13@uabc.edu.mx", "Holiii");
		
		if (editado) {
			// Se actualiza los recursos y las actividades seleccionadas
			
			// Se obtienen los recursos seleccionados anteriormente
			Set<Integer> setRecursosAnteriores = new HashSet<>();
			List<Map<String, Object>> recursosS = solicitudService.listarRecursos(solicitud.getIdSolicitud());
			for (Map<String, Object> recurso : recursosS) {
				// Verifica si el mapa contiene la clave "idRecurso"
				if ((Boolean) recurso.get("registrado")) {
					if (recurso.containsKey("idRecurso")) {
						// Obtén el valor asociado a la clave "idRecurso" y se agrega al conjunto
						setRecursosAnteriores.add((Integer) recurso.get("idRecurso"));
					}
				}
			}

			// Se obtienen los id de los recursos seleccionados actualmente
			Set<Integer> setRecursosActuales = new HashSet<>(recursos);

			Set<Integer> borrarRecursos = new HashSet<>(setRecursosAnteriores);
			Set<Integer> agregarRecursos = setRecursosActuales;

			// Se obtienen los recursos que se quitaron de la solicitud
			borrarRecursos.removeAll(setRecursosActuales);
			// Se obtienen los nuevos recursos seleccionados
			agregarRecursos.removeAll(setRecursosAnteriores);
			
			Boolean recursoRegistrado;
			Boolean resultRecurso = true;
			// Se intera sobre los nuevos recursos que fueron seleccionados
			for (Integer recurso : agregarRecursos) {

				// En caso de que se haya selccionado Transporte se registra su detalle
				if (recurso == 2) {
					recursoRegistrado = solicitudService.saveRecurso(idSolicitud, recurso, numPasajeros);

					// En caso de que se haya selccionado Otro se registra su detalle
				} else if (recurso == 5) {
					recursoRegistrado = solicitudService.saveRecurso(idSolicitud, recurso, otroRecurso);

					// Coso contrario se registra solo el recurso
				} else {
					recursoRegistrado = solicitudService.saveRecurso(idSolicitud, recurso, null);
				}
				if(!recursoRegistrado) {
					resultRecurso = false;
				}
			}
			// Se actualizan los detalles de los recursos si es que hubo un cambio
			if (recursos.contains(2)) {
				solicitudService.updateDetalleRecurso(idSolicitud, 2, numPasajeros);
			}
			if (recursos.contains(5)) {
				solicitudService.updateDetalleRecurso(idSolicitud, 5, otroRecurso);
			}
			// Se borran los recursos que fueron quitados de la solicitud
			solicitudService.deleteRecursos(idSolicitud, borrarRecursos);

			// Se actualizan las actividades

			// Se obtienen las actividades seleccionadas anteriormente
			Set<Integer> setActividadesAnteriores = new HashSet<>();
			
			List<Map<String, Object>> actividadesS = solicitudService.listarActividadesAsociadas(idSolicitud);
			for (Map<String, Object> actividad : actividadesS) {
				// Verifica si el mapa contiene la clave "idRecurso"
				if ((Boolean) actividad.get("registrado")) {
					if (actividad.containsKey("idActividad")) {
						// Obtén el valor asociado a la clave "idRecurso" y se agrega al conjunto
						setActividadesAnteriores.add((Integer) actividad.get("idActividad"));
					}
				}
			}

			// Se obtienen las actividades seleccionadas actualmente
			Set<Integer> setActividadesActuales = new HashSet<>(actividades);

			Set<Integer> borrarActividades = new HashSet<>(setActividadesAnteriores);
			Set<Integer> agregarActividades = setActividadesActuales;

			// Se obtienen las actividades que se quitaron de la solicitud
			borrarActividades.removeAll(setActividadesActuales);

			// Se obtienen las nuevas actividades fueron seleccionadas
			agregarActividades.removeAll(setActividadesAnteriores);
			
			Boolean actividadRegistrada;
			Boolean resultActividad = true;
			// Se intera sobre las nuevas actividades que fueron seleccionadas
			for (Integer actividad : agregarActividades) {

				// En caso de que se haya selccionado Otra se registra su detalle
				if (actividad == 6) {
					actividadRegistrada = solicitudService.saveActividad(idSolicitud, actividad, otroActividad);
				} else {
					// Coso contrario se registra solo la actividad
					actividadRegistrada = solicitudService.saveActividad(idSolicitud, actividad, null);
				}
				if(!actividadRegistrada) {
					resultActividad = false;
				}
			}

			// Actualizar detalle de Actividad si es necesario
			if (actividades.contains(6)) {
				solicitudService.updateDetalleActividad(idSolicitud, 6, otroActividad);
			}
			// Se borran las actividades que fueron quitadas de la solicitud
			solicitudService.deleteActividades(idSolicitud, borrarActividades);
			
			if(solicitud.getEstadoSolicitud().equalsIgnoreCase("En correción")) {
				System.out.println("Estaba en correcion");
				//Borrar Firmas 
				solicitudService.reiniciarFirmas(idSolicitud);
				// Enviar correos 
				String nombreUsuario = u.getUsername()+" " + u.getApPaterno() + " " + u.getApMaterno();
				List<Map<String, Object>> rolesFirmas = solicitudService.DatosRolesFirma(idSolicitud);
				System.out.println(nombreUsuario);
				System.out.println(rolesFirmas);
				// Se comento el envio de correos, para no mandar correos a usuarios reales
				//mailManager.Correcion(rolesFirmas, nombreUsuario, solicitud.getNombreEvento());
			}

			String resultadoFinal="";
			if(resultActividad && resultRecurso) {
				resultadoFinal = "<div class='alert alert-success' role='alert'> La solicitud fue actualizada </div>";
			}else {
				if(!resultActividad) {
					resultadoFinal += "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al editar las actividades</div>";
				}
				if(!resultRecurso) {
					resultadoFinal += "<div class='alert alert-danger' role='alert'>Ha ocurrido un error al editar los recursos </div>";
				}
			}


			return  resultadoFinal;
		} else {
			return "<div class='alert alert-danger' role='alert'> Ha ocurrido un error al intentar de editar la solcitud </div>";
		}
	}

}
