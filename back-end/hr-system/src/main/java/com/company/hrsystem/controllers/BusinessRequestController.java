package com.company.hrsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.hrsystem.constants.ApiUrlConstant;
import com.company.hrsystem.request.BusinessRequest;
import com.company.hrsystem.service.BusinessRequestService;

@RestController
@CrossOrigin
@RequestMapping(ApiUrlConstant.ROOT_API)
public class BusinessRequestController {

	@Autowired
	BusinessRequestService businessRequestService;

	@PostMapping(ApiUrlConstant.BUSINESS_INSERT_REQUEST)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')")
	public ResponseEntity<?> insertBusinessRequest(@RequestBody BusinessRequest request) {
		return ResponseEntity.ok(businessRequestService.insertBusinessRequest(request));
	}

	@PostMapping(ApiUrlConstant.BUSINESS_UPDATE_REQUEST)
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')")
	public ResponseEntity<?> updateBusinessRequest(@RequestBody BusinessRequest request) {
		return ResponseEntity.ok(businessRequestService.updateBusinessRequest(request));
	}

}
