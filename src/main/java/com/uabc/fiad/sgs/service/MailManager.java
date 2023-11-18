package com.uabc.fiad.sgs.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;


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
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setTo(email);
			helper.setText(mensaje);
			
			helper.setFrom(sender);
			javaMailSender.send(message);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void Correcion(List<String> correos, String nombre) {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		for(String correo : correos) {
			try {
				message.setSubject("Correción de solicitud");
				MimeMessageHelper helper = new MimeMessageHelper(message,true);
				helper.setTo(correo);
				helper.setText(nombre + " solita nueva firma, por correción de su solicitud.");
				
				helper.setFrom(sender);
				javaMailSender.send(message);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * Envía un correo indicando que una solicitud fue firmada
	 * @param correos	lista de correos a los cuales se les enviará la notificación
	 * @param nombre	nombre de la solicitud de salida
	 * @param firma		nombre del rol que firmó (Director, Subdirector, etc.)
	 */
	public void firmada(List<String> correos, String nombre, String firma) {
		MimeMessage message = javaMailSender.createMimeMessage();

		for(String correo : correos) {
			try {
				message.setSubject("Solicitud firmada");
				MimeMessageHelper helper = new MimeMessageHelper(message,true);
				helper.setTo(correo);
				helper.setText("La solicitud de salida a '" + nombre + "' ha sido firmada por " + firma + ".");

				helper.setFrom(sender);
				javaMailSender.send(message);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public void rechazada(String correo,String nombreSolicitante,  String evento) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setSubject("Correción de solicitud");
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setTo(correo);
			helper.setText("Estimado/a "+nombreSolicitante+".\r\n"
					+ "\r\n"
					+ "Espero que se encuentre bien. Me dirijo a usted para informarle que, tras revisar cuidadosamente su solicitud de participación en el evento "+evento+", lamentamos informarle que no podemos aprobar su solicitud en esta ocasión .\r\n"
					+ "\r\n"
					+ "Razones del Rechazo:\r\n"
					+ "Proporciona de manera respetuosa y clara las razones específicas del rechazo.]\r\n"
					+ "");
			
			helper.setFrom(sender);
			javaMailSender.send(message);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
