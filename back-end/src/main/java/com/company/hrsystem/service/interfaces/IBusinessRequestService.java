package com.company.hrsystem.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.CommentRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.request.MutipleUpdateRequestTicketStatusRequest;
import com.company.hrsystem.request.NotificationRequest;
import com.company.hrsystem.request.RequesterActionRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.response.ResponseData;

public interface IBusinessRequestService {

	public ResponseData insertBusinessRequest(BusinessRequest request, HttpServletRequest httpServletRequest);

	public ResponseData updateRequesterAction(RequesterActionRequest request,
			HttpServletRequest httpServletRequest);

	public ResponseData updateSupervisorAction(SupervisorActionRequest request,
			HttpServletRequest httpServletRequest);

	public ResponseData updateApproverAction(ApproverActionRequest request, HttpServletRequest httpServletRequest);

	public ResponseData insertComment(CommentRequest request, HttpServletRequest httpServletRequest);

	public ResponseData findListCreatedRequestTicket(FindListTicketRequest request);

	public ResponseData findRequestTicketById(Integer id);

	public ResponseData findListReceivedRequestTicket(FindListTicketRequest request);

	public ResponseData findCurrentUser();

	public ResponseData findAccountByRole(String role);

	public ResponseData findReason();

	public ResponseData findRequestType();

	public ResponseData findEmployeeId();

	public ResponseData findListComment(Integer id);

	public ResponseData mutipleUpdateRequestTicketStatus(MutipleUpdateRequestTicketStatusRequest request,
			HttpServletRequest httpServletRequest);

	public ResponseData findNotificationByReceiverId();

	public ResponseData markNotificationAsRead(NotificationRequest request);

	public ResponseData deleteNotificationByReceiver(NotificationRequest request);

}
