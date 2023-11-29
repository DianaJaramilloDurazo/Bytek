package com.uabc.fiad.sgs.controller;

import com.google.api.client.http.FileContent;
import com.uabc.fiad.sgs.entity.Filtros;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.DriveGoogleService;
import com.uabc.fiad.sgs.service.ISolicitudService;
import com.uabc.fiad.sgs.service.IUsuarioService;
import com.uabc.fiad.sgs.service.MailManager;
import com.uabc.fiad.sgs.utils.SessionUtils;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import jakarta.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping("solicitud")
public class SolicitudController {

	@Autowired
	private ISolicitudService solicitudService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private MailManager mailManager;


	@Autowired
	private DriveGoogleService driveService;
	

	@GetMapping("por-firmar")
	public String getSolicitudesPorFirmar(Model model) {
		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}

		// Si tiene rol
		// Lista de solicitudes para firmar
		List<Solicitud> solicitudesPendientes = solicitudService
				.listarSolicitudesPendientes(usuarioService.findIdRolById(u.getIdUsuario()));

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

	@PostMapping(value = "firmar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	@HxTrigger("refreshSolicitudesFirmar")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public String firmarSolicitud(@RequestParam Integer solicitudid) {

		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}

		boolean success = solicitudService.firmarSolicitud(solicitudid, u.getIdUsuario(),
				usuarioService.findIdRolById(u.getIdUsuario()));

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


	/**
	 * Método POST para rechazar una solicitud de salida.
	 * @param rechazarId	id de la solicitud a rechazar
	 * @param motivo		mensaje con el motivo del rechazo de la solicitud
	 * @return				notificación con resultado del proceos del rechazo de la solicitud
	 */
	@PostMapping(value = "rechazar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	@HxTrigger("refreshSolicitudesFirmar")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public String RechazarSolicitud(@RequestParam Integer rechazarId,@RequestParam String motivo) {


		System.out.println(rechazarId);
		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}
		
		System.out.print(motivo);
		boolean rechazado = solicitudService.rechzarSolicitud(rechazarId);
		if(rechazado) {
			solicitudService.reiniciarFirmas(rechazarId);
			Solicitud solicitud = solicitudService.findById(rechazarId).get();
			Usuario user = usuarioService.findById(solicitud.getIdUsuario()).get();
			String nombreUsuario = user.getUsername()+" " + user.getApPaterno() + " " + user.getApMaterno();
			// Comentado para no enviar correos a otros usuarios
			//mailManager.rechazada(user.getCorreo(), nombreUsuario, solicitud.getNombreEvento(),motivo);
			
			
			return "<div class='alert alert-success' role='alert'> La solicitud fue rechazada </div>";
			
		}else {
			return "<div class='alert alert-danger' role='alert'> Ha ocurrido un error al intentar rechzar la solcitud </div>";
		}
	}

	@PostMapping(value = "subirReporte", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, "multipart/form-data"  }, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public String subirReporte(@RequestParam Integer solicitud_id, @RequestParam MultipartFile reporte_archivo)
	throws IOException, GeneralSecurityException {
		System.out.println(solicitud_id);
		System.out.println(reporte_archivo.getSize());
		
		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}

		
		if(!reporte_archivo.getContentType().equals("application/pdf")){
			return "<div id='alertaResultadoReporte' class='alert alert-danger' role='alert'><b>El archivo no es un PDF </b></div>";
		}

		// getSize() regresa valor en bytes 
		// tamanio de archivo es mayor a 2.19MB
		if(reporte_archivo.getSize() >2200000){
			return "<div id='alertaResultadoReporte' class='alert alert-danger' role='alert'><b>El archivo es mayor a 2MB </b></div>";
		}  

		System.out.println(reporte_archivo.getOriginalFilename());
		String pathFileOS = "./" + reporte_archivo.getOriginalFilename();
		System.out.println(pathFileOS);
		 
		File driveFile = new File(pathFileOS);
		OutputStream os = new FileOutputStream(driveFile);
		os.write(reporte_archivo.getBytes());
		
		reporte_archivo.transferTo(driveFile);
		String idDrive = DriveGoogleService.uploadPDF(  driveFile) ;
		
		os.close();
		driveFile.delete();
		
		
		if(	idDrive == null)
		{
			
			return "<div id='alertaResultadoReporte' class='alert alert-danger' role='alert'><b>El archivo no fue subido<br>El servidor falló </b></div>";
		
		}
		


		System.out.println(idDrive);
		
		if(!solicitudService.guardarReferenciaReporteFinal(idDrive, solicitud_id)){
			return "<div id='alertaResultadoReporte' class='alert alert-danger' role='alert'><b>El archivo no fue subido<br>El servidor falló </b></div>";
		
		}



		return "<div id='alertaResultadoReporte' class='alert alert-success' role='alert'><b> El archivo fue subido con éxito </b></div>";
	
	}

	/**
	 * Obtiene una página de solicitudes paginada.
	 * @param filtros	los filstros aplicados, número de página, nombre, etc.
	 * @param model		el modelo utilizado para pasar datos a la vista
	 * @return			fragmento para mostrar la lista de solicitudes pendientes por firmar
	 */
	@GetMapping("/solicitudPagination")
	public String paginacion(@ModelAttribute Filtros filtros, Model model) {
		// Obtener lista de solicitudes
		Usuario u = SessionUtils.getUsuario(usuarioService);
		if (u == null) {
			return "redirect:/login";
		}
		System.out.println("HOLAA");
		// Manejar filtros
		System.out.println(filtros);
		model.addAttribute("filtros", filtros);

		// Tamaño de página: cuántos registros se mostrarán por página
		int pageSize = 4;
		// Calcular el desplazamiento (offset) para determinar desde qué registro se
		// debe iniciar en la base de datos
		int offset = (filtros.getPage() - 1) * pageSize;
		
		
		// Esto se hace para que no ocuura un erro al el firmar o rechazar una unica solicitud
		// que se encuentra en una sola, ya que al no hacer esto no se muestra ninguna solicitud
		List<Solicitud> solicitudesPendientes = solicitudService.PaginacionSolicitudesPendientes(pageSize, offset,
				filtros, usuarioService.findIdRolById(u.getIdUsuario()));
		
		// Si era la unica solicitud de la pagina, se verifica que pagina era 
		if(solicitudesPendientes.isEmpty()) {
			// Si es una página mayor a 1, se regresa a una página anterior
			if(filtros.getPage() - 1 > 0) {
				filtros.setPage(filtros.getPage() - 1);
			}else {
				filtros.setPage(1);
			}
			offset = (filtros.getPage() - 1) * pageSize;
		}
		
		// Obtener total de registros
		Integer totalRecords = solicitudService.totalSolicitudesPendientes(2);

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
		solicitudesPendientes = solicitudService.PaginacionSolicitudesPendientes(pageSize, offset,
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
		return "fragments/solicitud/solicitudes-por-firmar :: solicitudes-por-firmar";
	}



	@RequestMapping(value = "/descargarReporte")
	public boolean descargarReporte(@RequestParam("idR") String reporte_id)
	throws IOException, GeneralSecurityException {
			

		if (reporte_id == null){
			System.out.println("nohabia nombre");
			return false;
		}

		Map<ByteArrayOutputStream, Object> mapeOfileNombre = new HashMap<>();
		
		
		mapeOfileNombre =  DriveGoogleService.downloadPDF(reporte_id);	;
		
		ByteArrayOutputStream bao = (ByteArrayOutputStream) mapeOfileNombre.get("ByteArrayOutputStream");

		String nombre = (String) mapeOfileNombre.get("Object");
		
		
		System.out.println("EN CONTROLADOR");
		System.out.println("EN CONTROLADOR");
		System.out.println("EN CONTROLADOR");  
		System.out.println(bao);
		System.err.println(nombre);
		
		
		
		OutputStream outputStream = new FileOutputStream( nombre  );
		bao.writeTo(outputStream); 




		return true;
	
	}

}
