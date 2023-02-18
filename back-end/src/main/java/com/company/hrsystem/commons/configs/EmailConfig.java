package com.company.hrsystem.commons.configs;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {
	
	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private Integer port;

	@Value("${spring.mail.protocol}")
	private String protocol;

	@Value("${spring.mail.default-encoding}")
	private String defaultEncoding;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String smtpAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String starttlsEnable;

	@Value("${spring.mail.test-connection}")
	private String testConnection;

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setProtocol(protocol);
		javaMailSender.setDefaultEncoding(defaultEncoding);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", smtpAuth);
		javaMailProperties.put("mail.smtp.starttls.enable", starttlsEnable);
		javaMailProperties.put("mail.test-connection", testConnection);
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		return javaMailSender;
	}
}
