package com.company.hrsystem.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.company.hrsystem.config.SystemProperties;

public class MessageUtil {

	private static MessageUtil instance;

	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void registerInstance() {
		instance = this;
	}

	public static String getMessagelangUS(String messCode) {
		return instance.messageSource.getMessage(messCode, null, SystemProperties.SYSTEM_LANG_US);
	}

	public static String getFlexMessageLangUS(String flexMessCode, String replaceName) {
		return instance.messageSource.getMessage(flexMessCode, null, SystemProperties.SYSTEM_LANG_US).replace("{0}",
				replaceName);
	}

}
