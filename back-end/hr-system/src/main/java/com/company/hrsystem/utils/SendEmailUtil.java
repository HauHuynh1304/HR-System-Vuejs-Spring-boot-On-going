package com.company.hrsystem.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.company.hrsystem.config.SystemProperties;
import com.company.hrsystem.dto.EmailDto;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class SendEmailUtil {

	private static SendEmailUtil instance;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Configuration freemarkerConfig;

	@PostConstruct
	public void registerInstance() {
		instance = this;
	}

	public static void sendSimpleEmail(EmailDto emailInfo) throws Exception {
		MimeMessage message = instance.emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		Template template = instance.freemarkerConfig.getTemplate(emailInfo.getEmailTemplate());
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, setMapEmailData(emailInfo));

		helper.setFrom(SystemProperties.MAIL_USER_NAME);
		helper.setTo(emailInfo.getTo());
		if (ArrayUtils.isNotEmpty(emailInfo.getCc())) {
			helper.setCc(emailInfo.getCc());
		}

		if (ArrayUtils.isNotEmpty(emailInfo.getBcc())) {
			helper.setCc(emailInfo.getBcc());
		}
		helper.setText(html, true);
		helper.setSubject(emailInfo.getSubject());

		instance.emailSender.send(message);
	}

	private static Map<String, Object> setMapEmailData(EmailDto email) {
		Map<String, Object> model = new HashMap<>();
		model.put("data", email);
		return model;
	}

}
