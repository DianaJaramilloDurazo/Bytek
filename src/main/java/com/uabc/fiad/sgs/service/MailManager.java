package com.uabc.fiad.sgs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.mail.internet.MimeMessage;


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

}
