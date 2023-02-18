package com.company.hrsystem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.hrsystem.commons.constants.ApiUrlConstant;
import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.CommentRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.request.MutipleUpdateRequestTicketStatusRequest;
import com.company.hrsystem.request.NotificationRequest;
import com.company.hrsystem.request.RequesterActionRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.service.BusinessRequestServiceImpl;

@RestController
@CrossOrigin
@RequestMapping(ApiUrlConstant.ROOT_API)
public class BusinessRequestController {

	@Autowired
	BusinessRequestServiceImpl businessRequestService;

	@PostMapping(ApiUrlConstant.BUSINESS_INSERT_REQUEST)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> insertBusinessRequest(@RequestBody BusinessRequest request,
			HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(businessRequestService.insertBusinessRequest(request, httpServletRequest));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_REQUESTER_ACTION)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> updateRequesterAction(@RequestBody RequesterActionRequest request,
			HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(businessRequestService.updateRequesterAction(request, httpServletRequest));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_SUPERVISOR_ACTION)
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	public ResponseEntity<?> updateSupervisorAction(@RequestBody SupervisorActionRequest request,
			HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(businessRequestService.updateSupervisorAction(request, httpServletRequest));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_APPROVER_ACTION)
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<?> updateApproverAction(@RequestBody ApproverActionRequest request,
			HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(businessRequestService.updateApproverAction(request, httpServletRequest));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_INSERT_COMMENT)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_EMPLOYEE')")
	public ResponseEntity<?> insertComment(@RequestBody CommentRequest request, HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(businessRequestService.insertComment(request, httpServletRequest));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_SEARCH_LIST_CREATED_REQUEST)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findListCreatedRequestTicket(@RequestBody FindListTicketRequest request) {
		return ResponseEntity.ok(businessRequestService.findListCreatedRequestTicket(request));
	}

	@GetMapping(ApiUrlConstant.BUSINESS_SEARCH_REQUEST_BY_ID)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findRequestTicketById(@PathVariable Integer id) {
		return ResponseEntity.ok(businessRequestService.findRequestTicketById(id));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_SEARCH_LIST_RECEIVED_REQUEST)
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
	public ResponseEntity<?> findListReceivedRequestTicket(@RequestBody FindListTicketRequest request) {
		return ResponseEntity.ok(businessRequestService.findListReceivedRequestTicket(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_FIND_CURRENT_USER)
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN', 'ROLE_ROOT_ADMIN')")
	public ResponseEntity<?> findCurrentUser() {
		return ResponseEntity.ok(businessRequestService.findCurrentUser());
	}

	@GetMapping(ApiUrlConstant.BUSINSES_FIND_ACCOUNT_BY_ROLE)
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findAccountByRole(@PathVariable(required = true) String role) {
		return ResponseEntity.ok(businessRequestService.findAccountByRole(role));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_FIND_REASON)
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findAllReason() {
		return ResponseEntity.ok(businessRequestService.findReason());
	}

	@PostMapping(ApiUrlConstant.BUSINESS_FIND_REQUEST_TYPE)
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findRequestType() {
		return ResponseEntity.ok(businessRequestService.findRequestType());
	}

	@PostMapping(ApiUrlConstant.BUSINESS_FIND_EMPLOYEE_ID)
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findEmployeeId() {
		return ResponseEntity.ok(businessRequestService.findEmployeeId());
	}

	@GetMapping(ApiUrlConstant.BUSINESS_FIND_LIST_COMMENT)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findListComment(@PathVariable Integer id) {
		return ResponseEntity.ok(businessRequestService.findListComment(id));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_MUTIPLE_UPDATE_REQUEST_TICKET_STATUS)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR')")
	public ResponseEntity<?> mutipleUpdateRequestTicketStatus(
			@RequestBody MutipleUpdateRequestTicketStatusRequest request, HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(businessRequestService.mutipleUpdateRequestTicketStatus(request, httpServletRequest));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_FIND_NOTIFICATION_BY_RECEIVER_ID)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR','ROLE_EMPLOYEE')")
	public ResponseEntity<?> findNotificationByReceiverId() {
		return ResponseEntity.ok(businessRequestService.findNotificationByReceiverId());
	}

	@PostMapping(ApiUrlConstant.BUSINESS_MARK_NOTIFICATION_AS_READ)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_EMPLOYEE')")
	public ResponseEntity<?> markNotificationAsRead(@RequestBody NotificationRequest request) {
		return ResponseEntity.ok(businessRequestService.markNotificationAsRead(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_DELETE_NOTIFICATION_BY_RECEIVER)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_EMPLOYEE')")
	public ResponseEntity<?> deleteNotificationByReceiver(@RequestBody NotificationRequest request) {
		return ResponseEntity.ok(businessRequestService.deleteNotificationByReceiver(request));
	}

}
