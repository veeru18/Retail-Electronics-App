package com.ecommerce.retail_electronicsapp.mailservice;

import java.util.Date;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MailService {

	private JavaMailSender javaMailSender;
	
	public void sendMessage(MessageModel messageModel) throws MessagingException {
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
		helper.setTo(messageModel.getTo());
		helper.setSentDate(new Date());
		helper.setSubject(messageModel.getSubject());
		helper.setText(messageModel.getMessage(), true);
		
		javaMailSender.send(mimeMessage);
	}
}
