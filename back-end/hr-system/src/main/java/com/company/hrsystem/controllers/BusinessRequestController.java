package com.company.hrsystem.controllers;

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

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.ApproverActionRequest;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.request.CommentRequest;
import com.company.hrsystem.request.FindListTicketRequest;
import com.company.hrsystem.request.SupervisorActionRequest;
import com.company.hrsystem.service.BusinessRequestService;

@RestController
@CrossOrigin
@RequestMapping(ApiUrlConstant.ROOT_API)
public class BusinessRequestController {

	@Autowired
	BusinessRequestService businessRequestService;

	@PostMapping(ApiUrlConstant.BUSINESS_INSERT_REQUEST)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> insertBusinessRequest(@RequestBody BusinessRequest request) {
		return ResponseEntity.ok(businessRequestService.insertBusinessRequest(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_REQUEST)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> updateBusinessRequest(@RequestBody BusinessRequest request) {
		return ResponseEntity.ok(businessRequestService.updateBusinessRequest(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_SUPERVISOR_ACTION)
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	public ResponseEntity<?> updateSupervisorAction(@RequestBody SupervisorActionRequest request) {
		return ResponseEntity.ok(businessRequestService.updateSupervisorAction(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_APPROVER_ACTION)
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<?> updateApproverAction(@RequestBody ApproverActionRequest request) {
		return ResponseEntity.ok(businessRequestService.updateApproverAction(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_INSERT_COMMENT)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR')")
	public ResponseEntity<?> insertComment(@RequestBody CommentRequest request) {
		return ResponseEntity.ok(businessRequestService.insertComment(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_SEARCH_LIST_REQUEST_BY_CURRENT_USER)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findListTicketRequestByCurrentUser(@RequestBody FindListTicketRequest request) {
		return ResponseEntity.ok(businessRequestService.findListTicketRequestByCurrentUser(request));
	}

	@GetMapping(ApiUrlConstant.BUSINESS_SEARCH_REQUEST_BY_ID)
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> findTicketRequestById(@PathVariable String id) {
		return ResponseEntity.ok(businessRequestService.findTicketRequestById(id));
	}

}
