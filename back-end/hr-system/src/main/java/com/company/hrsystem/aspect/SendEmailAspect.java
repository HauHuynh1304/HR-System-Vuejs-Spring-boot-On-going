package com.company.hrsystem.aspect;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.hrsystem.annotations.SendEmail;
import com.company.hrsystem.constants.CommonConstant;
import com.company.hrsystem.dto.EmailDto;
import com.company.hrsystem.mapper.RequestTypeMapper;
import com.company.hrsystem.mapper.SystemAccountMapper;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.utils.LogUtil;
import com.company.hrsystem.utils.SendEmailUtil;
import com.company.hrsystem.utils.StringUtil;

@Aspect
@Component
public class SendEmailAspect {

	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Autowired
	private RequestTypeMapper requestTypeMapper;

	@Pointcut(value = "@annotation(sendEmail)")
	public void sendEmailAfterSuccessPointCut(SendEmail sendEmail) {
	}

	@AfterReturning("sendEmailAfterSuccessPointCut(sendEmail) && args(request, httpServletRequest)")
	public void doSendEmail(SendEmail sendEmail, Object request, HttpServletRequest httpServletRequest) {
		EmailDto emailInfo = new EmailDto();
		switch (sendEmail.mailType()) {
		case CommonConstant.EMAIL_NEW_REQUEST_TYPE:
			BusinessRequest businessRequest = (BusinessRequest) request;
			List<String> cc = new ArrayList<>();
			String receiverEmail = systemAccountMapper.findSystemEmailByEmployeeId(
					businessRequest.getData().getSupervisorAcction().getSupervisorId());
			String senderEmail = systemAccountMapper
					.findSystemEmailByEmployeeId(businessRequest.getData().getRequestEmployee().getEmployeeId());

			emailInfo.setTo(receiverEmail);

			cc.add(systemAccountMapper
					.findSystemEmailByEmployeeId(businessRequest.getData().getApproverAction().getApproverId()));
			cc.add(senderEmail);
			emailInfo.setCc(cc.stream().toArray(String[]::new));

			emailInfo.setEmailTemplate("NewRequestTicketNotification.ftlh");

			emailInfo.setReceiverName(StringUtil.subStringByEmailSign(receiverEmail));

			emailInfo.setSenderName(StringUtil.subStringByEmailSign(senderEmail));

			emailInfo.setRequestType(
					requestTypeMapper.findRequestTypeById(businessRequest.getData().getRequest().getRequestTypeId()));
			
			emailInfo.setSubject(sendEmail.subject());
			break;
		default:
			break;
		}
		try {
			SendEmailUtil.sendSimpleEmail(emailInfo);
		} catch (Exception e) {
			LogUtil.error("something went wrong when trying send email");
			LogUtil.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
