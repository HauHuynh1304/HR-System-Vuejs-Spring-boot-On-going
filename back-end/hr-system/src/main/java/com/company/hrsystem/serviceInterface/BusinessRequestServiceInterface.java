package com.company.hrsystem.serviceInterface;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.dto.ApproverActionDto;
import com.company.hrsystem.dto.SupervisorActionDto;
import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.CommentRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.request.MutipleUpdateRequestTicketStatusRequest;
import com.company.hrsystem.request.NotificationRequest;
import com.company.hrsystem.request.RequesterActionRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.response.ResponseTemplate;

public interface BusinessRequestServiceInterface {

	public ResponseTemplate insertBusinessRequest(BusinessRequest request, HttpServletRequest httpServletRequest);

	public ResponseTemplate updateRequesterAction(RequesterActionRequest request,
			HttpServletRequest httpServletRequest);

	public ResponseTemplate updateSupervisorAction(SupervisorActionRequest request,
			HttpServletRequest httpServletRequest);

	public ResponseTemplate updateApproverAction(ApproverActionRequest request, HttpServletRequest httpServletRequest);

	public ResponseTemplate insertComment(CommentRequest request, HttpServletRequest httpServletRequest);

	public ResponseTemplate findListCreatedRequestTicket(FindListTicketRequest request);

	public ResponseTemplate findRequestTicketById(Integer id);

	public ResponseTemplate findListReceivedRequestTicket(FindListTicketRequest request);

	public ResponseTemplate findCurrentUser();

	public ResponseTemplate findAccountByRole(String role);

	public ResponseTemplate findReason();

	public ResponseTemplate findRequestType();

	public ResponseTemplate findEmployeeId();

	public ResponseTemplate findListComment(Integer id);

	public ResponseTemplate mutipleUpdateRequestTicketStatus(MutipleUpdateRequestTicketStatusRequest request,
			HttpServletRequest httpServletRequest);

	public ResponseTemplate findNotificationByReceiverId();

	public ResponseTemplate markNotificationAsRead(NotificationRequest request);

	public ResponseTemplate deleteNotificationByReceiver(NotificationRequest request);

	public void updateSupervisorAction(SupervisorActionDto supervisorActionDto, HttpServletRequest httpServletRequest);

	public void updateApproverAction(ApproverActionDto approverActionDto, HttpServletRequest httpServletRequest);

}
