package com.uabc.fiad.sgs.controller;

import com.uabc.fiad.sgs.entity.Rol;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.service.UsuarioService;
import com.uabc.fiad.sgs.utils.SessionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
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
	 * @param model		modelo utilizado para pasar datos a la vista
	 * @return  		fragmento de thymeleaf con el cuerpo del formulario
	 */
	@GetMapping("/get-registrar-solicitud-form")
	public String getForm(Model model) {
		System.out.println("Golaa");
		model.addAttribute("carreras", usuarioService.listarCarreras());
		return "fragments/solicitud/registrar-solicitud-form :: registrar-solicitud-form";
	}
	/**
	 * Guarda una solictud de salida Generada por el docente
	 * @param solicitud		solicitud a guardar, con algunos datos
	 * @param fechaSalida	fecha de salida
	 * @param fechaRegreso	fecha de regreso
	 * @param horaSalida	hora de salida 
	 * @param horaRegreso	hora de regreso
	 * @param recursos		recursos a utilizar para la salida
	 * @param actividades	actividades a realziar en la salida 
	 * @param numPasajeros	numero de pasajero en caso de seleccionar transporte
	 * @param litros		listros de gasolina en caso de seleccionar combustible
	 * @param otroRecurso	otro recurso en caso de que se seleccione 
	 * @param otroActividad otra actividad en caso de ser seleccionada 
	 * @return				mensaje que indica si se registro o no la solicitud
	 */
	@PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public String registrarSolicitud(Solicitud solicitud,
			@RequestParam(value="fSalida") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaSalida,
			@RequestParam(value="fRegreso") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaRegreso,
			@DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaSalida,
			@DateTimeFormat(pattern = "HH:mm:ss") LocalTime horaRegreso,
			@RequestParam(value="recursos") List<String> recursos,
			@RequestParam(value="actividades") List<String> actividades,
			@RequestParam(value="transporte", required = false) Integer numPasajeros,
			@RequestParam(value="combustible", required = false) Integer litros,
			@RequestParam(value="otroRecurso", required = false) String otroRecurso,
			@RequestParam(value="otroRecurso", required = false) String otroActividad){
		
		// Se obtiene el usuario de la sesión actual 
		Usuario u = SessionUtils.getUsuario(usuarioService);
		//Se obtine su id
		solicitud.setIdUsuario(u.getIdUsuario());
		
		//Se guarda la fehca y hora 
		solicitud.setFechaSalida(fechaSalida.atTime(horaSalida));
		solicitud.setFechaRegreso(fechaRegreso.atTime(horaRegreso));
		
		// Se guarda la solicitud y se obtiene su id 
		Integer idSolicitud = solicitudService.saveSolicitud(solicitud);
		if(idSolicitud != 0) {
			//Registrar Recursos 
			
			return "<p>Registrada</p>";
		}
		return "<p>Error</p>";
	}

}
