package com.uabc.fiad.sgs.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MailManager {

	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public MailManager(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMessaje(String email, String mensaje) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setSubject("Prueba mensaje");
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
			helper.setText(mensaje);

			helper.setFrom(sender);
			// javaMailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Envía un correos solicitando nuevas firmas por correción de solictud
	 * 
	 * @param rolesFirmas Datos de los enecargados a firmar (nombre del rol y
	 *                    correo)
	 * @param nombre      Nombre completo del solicitante
	 * @param evento      Nombre del evento de la solicitud
	 */
	public void Correcion(List<Map<String, Object>> rolesFirmas, String nombre, String evento) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			for (Map<String, Object> datos : rolesFirmas) {
				String rolDescripcion = (String) datos.get("Rol_Descripcion");
				String correoRol = (String) datos.get("Correo_rol");
				message.setSubject("Correción de solicitud");
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(correoRol);
				helper.setText("Estimado/a " + rolDescripcion + "\r\n" + "\r\n"
						+ "Espero que se encuentre bien. Me dirijo a usted para solicitar nuevamente la firma de mi solicitud de salida para el evento "
						+ evento + " \r\n" + "\r\n"
						+ "Agradezco de antemano su atención y colaboración en este asunto. Si tiene alguna pregunta o necesita información adicional, no dude en ponerse en contacto conmigo.\r\n"
						+ "\r\n" + "Atentamente,\r\n" + "\r\n" + "" + nombre + "\r\n" + "");

				helper.setFrom(sender);
				// javaMailSender.send(message);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Envía un correos solicitando firmas por creación de solicitud
	 * 
	 * @param rolesFirmas Datos de los enecargados a firmar (nombre del rol y
	 *                    correo)
	 * @param nombre      Nombre completo del solicitante
	 * @param evento      Nombre del evento de la solicitud
	 */
	public void solicitarFirmas(List<Map<String, Object>> rolesFirmas, String nombre, String evento) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			for (Map<String, Object> datos : rolesFirmas) {
				String rolDescripcion = (String) datos.get("Rol_Descripcion");
				String correoRol = (String) datos.get("Correo_rol");
				message.setSubject("Petición de firma");
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(correoRol);
				helper.setText("Estimado/a " + rolDescripcion + "\r\n" + "\r\n"
						+ "Espero que se encuentre bien. Me dirijo a usted para solicitar la firma de mi solicitud de salida para el evento "
						+ evento + " \r\n" + "\r\n"
						+ "Agradezco de antemano su atención y colaboración en este asunto. Si tiene alguna pregunta o necesita información adicional, no dude en ponerse en contacto conmigo.\r\n"
						+ "\r\n" + "Atentamente,\r\n" + "\r\n" + "" + nombre + "\r\n" + "");

				helper.setFrom(sender);
				// javaMailSender.send(message);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Envía un correo indicando que una solicitud fue firmada
	 * 
	 * @param correos lista de correos a los cuales se les enviará la notificación
	 * @param nombre  nombre de la solicitud de salida
	 * @param firma   nombre del rol que firmó (Director, Subdirector, etc.)
	 */
	public void firmada(List<String> correos, String nombre, String firma) {
		MimeMessage message = javaMailSender.createMimeMessage();

		for (String correo : correos) {
			try {
				message.setSubject("Solicitud firmada");
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(correo);
				helper.setText("La solicitud de salida a '" + nombre + "' ha sido firmada por " + firma + ".");

				helper.setFrom(sender);
				// javaMailSender.send(message);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * Envía un correo de que la solictud fue rechazada
	 * 
	 * @param correo            correo del usuario a notificar
	 * @param nombreSolicitante nombre del usuario dueño de la solicitud
	 * @param evento            Nombre del evento de la solcitud
	 * @param motivos           motivos por los que fue rechado al solicitud
	 */
	public void rechazada(String correo, String nombreSolicitante, String evento, String motivos) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setSubject("Rechazo de solicitud");
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(correo);
			helper.setText("Estimado/a " + nombreSolicitante + ".\r\n" + "\r\n"
					+ "Espero que se encuentre bien. Me dirijo a usted para informarle que, tras revisar cuidadosamente su solicitud de participación en el evento "
					+ evento + ", lamentamos informarle que no podemos aprobar su solicitud en esta ocasión .\r\n"
					+ "\r\n" + "Razones del Rechazo:\r\n" + motivos + "");

			helper.setFrom(sender);
			// javaMailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
