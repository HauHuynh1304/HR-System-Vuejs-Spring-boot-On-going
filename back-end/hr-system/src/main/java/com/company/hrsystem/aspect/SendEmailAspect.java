package com.company.hrsystem.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.company.hrsystem.annotations.SendEmail;

@Aspect
@Component
public class SendEmailAspect {

	@Pointcut(value = "@annotation(sendEmail)")
	public void sendEmailAfterSuccessPointCut(SendEmail sendEmail) {
	}
	
	@AfterReturning("sendEmailAfterSuccessPointCut(sendEmail)")
	public void doSendEmail(SendEmail sendEmail) {
		System.err.println(sendEmail.subject());
	}
}
