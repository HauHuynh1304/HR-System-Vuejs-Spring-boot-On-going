package com.company.hrsystem.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setUsername(SystemProperties.MAIL_USER_NAME);
		javaMailSender.setPassword(SystemProperties.MAIL_PASSWORD);
		javaMailSender.setHost(SystemProperties.MAIL_HOST);
		javaMailSender.setPort(SystemProperties.MAIL_PORT);
		javaMailSender.setProtocol(SystemProperties.MAIL_PROTOCOL);
		javaMailSender.setDefaultEncoding(SystemProperties.MAIL_DEFAUL_ENCODING);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", SystemProperties.MAIL_AUTH);
		javaMailProperties.put("mail.smtp.starttls.enable", SystemProperties.MAIL_STARTTLS_ENABLE);
		javaMailProperties.put("mail.test-connection", SystemProperties.MAIL_TEST_CONNECTING);
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		return javaMailSender;
	}
}
