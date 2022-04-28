package com.company.hrsystem.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

	@Value("${system.lang.en}")
	Locale systemLangUS;

	@Autowired
	private MessageSource messageSource;

	public String getMessagelangUS(String messCode) {
		return messageSource.getMessage(messCode, null, systemLangUS);
	}

	public String getFlexMessageLangUS(String flexMessCode, String filedName) {
		return messageSource.getMessage(flexMessCode, null, systemLangUS).replace("{0}", filedName);
	}

}
